package com.megacrit.cardcrawl.actions.utility;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawOrRetrieveWeaveAction extends AbstractGameAction
{
    private AbstractCard card;
    
    public DrawOrRetrieveWeaveAction(AbstractCard card)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.card = card;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.drawPile.contains(card) || player.discardPile.contains(card))
            {
                addToBot(new MoveCardToHandAction(card));
            }
        }
        
        tickDuration();
        isDone = true;
    }
}
