package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

public class ImpossiblyFastAction extends AbstractGameAction
{
    private AbstractPlayer player;
    private int numCardsToPick;
    
    public ImpossiblyFastAction(int numCardsToDraw)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numCardsToPick = numCardsToDraw;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            AbstractPower noDrawPower = player.getPower(NoDrawPower.POWER_ID);
            if (noDrawPower != null)
            {
                noDrawPower.flash();
                isDone = true;
            }
            else
            {
                //region first update
                CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard drawPileCard : player.drawPile.group)
                {
                    if (drawPileCard.costForTurn == 0)
                    {
                        possibleCards.group.add(drawPileCard);
                    }
                }
    
                possibleCards.shuffle();
    
                for (int i = 0; i < numCardsToPick; i++)
                {
                    if (!possibleCards.isEmpty())
                    {
                        AbstractCard zeroCostCard = possibleCards.getTopCard();
                        possibleCards.removeTopCard();
            
                        addToBot(new DrawSpecificCardAction(zeroCostCard));
                    }
                }
    
                isDone = true;
                //endregion
            }
        }
    }
}
