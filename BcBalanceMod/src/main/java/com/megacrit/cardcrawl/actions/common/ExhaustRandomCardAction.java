package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustRandomCardAction extends AbstractGameAction
{
    int maxCost;
    
    public ExhaustRandomCardAction(int maxCost)
    {
        this.maxCost = maxCost;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            AbstractPlayer player = AbstractDungeon.player;
            CardGroup validExhaustTargets = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : player.hand.group)
            {
                if (maxCost < 0) //exhaust anything
                {
                    validExhaustTargets.addToBottom(card);
                }
                else if ((card.costForTurn <= maxCost) && (card.cost != -1)) //x-cost cards shouldn't count as less than any certain energy value
                {
                    validExhaustTargets.addToBottom(card);
                }
            }
            
            if (!validExhaustTargets.isEmpty())
            {
                player.hand.moveToExhaustPile(validExhaustTargets.getRandomCard(AbstractDungeon.cardRandomRng));
            }
        }
        
        tickDuration();
    }
}
