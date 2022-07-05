package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class QuickSlashAction extends AbstractGameAction
{
    public QuickSlashAction()
    {
        this.duration = 0.0F;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard card : DrawCardAction.drawnCards)
        {
            if ((card.costForTurn == 0) ||
                        card.freeToPlayOnce ||
                        ((card.cost == -2) && (card.type == AbstractCard.CardType.SKILL)))
            {
                if ((player.drawPile.size() == 0) && (player.discardPile.size() == 1))
                {
                    //dont draw, because it will just draw itself. that 1 card in the discard pile is the quickslash.
                }
                else
                {
                    addToBot(new DrawCardAction(1));
                }
                break;
            }
        }
        
        this.isDone = true;
    }
}
