package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class MakeTempCardAtBottomOfDeckAction extends AbstractGameAction {
    public MakeTempCardAtBottomOfDeckAction(int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
    }
    
    public void update() {
        if (this.duration == this.startDuration) {
            for(int i = 0; i < this.amount; ++i) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat().makeStatEquivalentCopy();
                UnlockTracker.markCardAsSeen(c.cardID);
                for(AbstractPower power : AbstractDungeon.player.powers)
                {
                    power.onCardCreated(c);
                }
                
                AbstractDungeon.player.drawPile.addToBottom(c);
            }
            
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        
        this.tickDuration();
    }
}
