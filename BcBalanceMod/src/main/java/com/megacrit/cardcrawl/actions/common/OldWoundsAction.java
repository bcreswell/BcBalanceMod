package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

public class OldWoundsAction extends AbstractGameAction
{
    private AbstractPlayer player;
    private int numCardsToPick;
    private  boolean upgraded;
    private int blockPerStatus;
    
    public OldWoundsAction(int numCardsToDraw, boolean upgraded, int blockPerStatus)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numCardsToPick = numCardsToDraw;
        this.upgraded = upgraded;
        this.blockPerStatus = blockPerStatus;
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
                CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard drawPileCard : player.drawPile.group)
                {
                    if (drawPileCard.type == AbstractCard.CardType.STATUS)
                    {
                        possibleCards.group.add(drawPileCard);
                    }
                }
                
                possibleCards.shuffle();
                
                int cardsToDraw = Math.min(numCardsToPick, possibleCards.size());
                cardsToDraw = Math.min(cardsToDraw, (10-player.hand.size()));
                
                int blockToGain = 0;
                for (int i = 0; i < cardsToDraw; i++)
                {
                    if (!possibleCards.isEmpty())
                    {
                        AbstractCard statusCard = possibleCards.getTopCard();
                        possibleCards.removeTopCard();
                        if (upgraded)
                        {
                            BcUtility.makeCardEthereal(statusCard);
                        }
                        addToBot(new DrawSpecificCardAction(statusCard));
                        blockToGain += blockPerStatus;
                    }
                }
                
                if (blockToGain > 0)
                {
                    addToBot(new GainBlockAction(player, blockToGain));
                }
                
                isDone = true;
            }
        }
    }
}
