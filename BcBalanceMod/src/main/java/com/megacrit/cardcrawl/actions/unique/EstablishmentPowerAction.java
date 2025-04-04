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
        CardGroup retainCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        //prefer cards that aren't temporarily zero already
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c.costForTurn > 0) && (c.cost != -1) &&
                (c.selfRetain || c.retain) &&
                !c.isEthereal)
            {
                retainCards.group.add(c);
            }
        }
        
        if (retainCards.isEmpty())
        {
            //try again with actual cost
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ((c.cost > 0) && (c.cost != -1) &&
                    (c.selfRetain || c.retain) &&
                    !c.isEthereal)
                {
                    retainCards.group.add(c);
                }
            }
        }
        
        if (retainCards.isEmpty())
        {
            //try again with ethereal
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ((c.cost != -1) &&
                    (c.selfRetain || c.retain))
                {
                    retainCards.group.add(c);
                }
            }
        }
        
        if (!retainCards.isEmpty())
        {
            CardGroup reducedCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < cardCountToReduce; i++)
            {
                if (!retainCards.isEmpty())
                {
                    AbstractCard cardToReduce = retainCards.getRandomCard(true);
                    cardToReduce.modifyCostForCombat(-1);
                    reducedCards.addToTop(cardToReduce);
                    retainCards.removeCard(cardToReduce);
                }
//                else
//                {
//                //shouldn't be able to hit the same card twice
////                    AbstractCard cardToReduce = reducedCards.getRandomCard(true);
////                    cardToReduce.modifyCostForCombat(-1);
//                }
            }
        }
        
        isDone = true;
    }
}
