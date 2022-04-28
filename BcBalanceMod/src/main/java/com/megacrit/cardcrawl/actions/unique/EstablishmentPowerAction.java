package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class EstablishmentPowerAction extends AbstractGameAction
{
    private int cardCountToReduce;
    
    public EstablishmentPowerAction(int cardCountToReduce)
    {
        this.cardCountToReduce = cardCountToReduce;
    }
    
    public void update()
    {
        //prefer cards that aren't temporarily zero already
        CardGroup retainCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c.costForTurn > 0) &&
                        ((c.type == AbstractCard.CardType.SKILL) || (c.type == AbstractCard.CardType.ATTACK)) &&
                        (c.selfRetain || c.retain))
            {
                retainCards.group.add(c);
            }
        }
        
        if (retainCards.isEmpty())
        {
            //try again with actual cost
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ((c.cost > 0) &&
                            ((c.type == AbstractCard.CardType.SKILL) || (c.type == AbstractCard.CardType.ATTACK)) &&
                            (c.selfRetain || c.retain))
                {
                    retainCards.group.add(c);
                }
            }
        }
        
        retainCards.shuffle();
        
        //only reduce each card once. No double reduction for a single card.
        for (int i = 0; i < cardCountToReduce; i++)
        {
            if (!retainCards.isEmpty())
            {
                AbstractCard cardToReduce = retainCards.getRandomCard(true);
                retainCards.removeCard(cardToReduce);
                
                cardToReduce.modifyCostForCombat(-1);
            }
        }
        
        isDone = true;
    }
}
