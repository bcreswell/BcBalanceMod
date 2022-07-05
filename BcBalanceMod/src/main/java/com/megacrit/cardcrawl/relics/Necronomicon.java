package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.Iterator;

public class Necronomicon extends AbstractRelic {
    public static final String ID = "Necronomicon";
    private static final int COST_THRESHOLD = 2;
    private boolean activated = true;
    
    public Necronomicon() {
        super("Necronomicon", "necronomicon.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
    }
    
    public void onEquip() {
        CardCrawlGame.sound.play("NECRONOMICON");
        description = DESCRIPTIONS[0] + 2 + DESCRIPTIONS[2];
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Necronomicurse(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        UnlockTracker.markCardAsSeen("Necronomicurse");
    }
    
    public void onUnequip() {
        AbstractCard cardToRemove = null;
        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();
        
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c instanceof Necronomicurse) {
                cardToRemove = c;
                break;
            }
        }
        
        if (cardToRemove != null) {
            AbstractDungeon.player.masterDeck.group.remove(cardToRemove);
        }
        
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && (card.costForTurn >= 2 && !card.freeToPlayOnce || card.cost == -1 && card.energyOnUse >= 2) && activated) {
            activated = false;
            grayscale = true;
            flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }
            
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            pulse = false;
        }
        
    }
    
    public void onVictory()
    {
        grayscale = false;
    }
    
    public void atTurnStart() {
        activated = true;
        grayscale = false;
    }
    
    public boolean checkTrigger() {
        return activated;
    }
    
    public AbstractRelic makeCopy() {
        return new Necronomicon();
    }
}
