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
            if ((c.costForTurn > 0) && (c.cost != -1) &&
                        (c.rarity != AbstractCard.CardRarity.RARE) &&
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
                            (c.rarity != AbstractCard.CardRarity.RARE) &&
                            (c.selfRetain || c.retain) &&
                            !c.isEthereal)
                {
                    retainCards.group.add(c);
                }
            }
        }
        
        if (retainCards.isEmpty())
        {
            //try again with actual cost
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ((c.cost != -1) &&
                            (c.rarity != AbstractCard.CardRarity.RARE) &&
                            (c.selfRetain || c.retain))
                {
                    retainCards.group.add(c);
                }
            }
        }
        
        if (!retainCards.isEmpty())
        {
            for (int i = 0; i < cardCountToReduce; i++)
            {
                AbstractCard cardToReduce = retainCards.getRandomCard(true);
                cardToReduce.modifyCostForCombat(-1);
            }
        }
        
        isDone = true;
    }
}
