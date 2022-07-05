package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcScrapeAction extends AbstractGameAction
{
    private AbstractPlayer player;
    private int numCardsToSearch;
    
    public BcScrapeAction(int numCardsToDraw)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numCardsToSearch = numCardsToDraw;
    }
    
    void moveCardToHand(AbstractCard card)
    {
        if (player.drawPile.contains(card))
        {
            if (player.hand.size() == 10)
            {
                player.drawPile.moveToDiscardPile(card);
                player.createHandIsFullDialog();
            }
            else
            {
                player.drawPile.moveToHand(card, player.drawPile);
            }
        }
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            //region first update
            CardGroup zeroCostCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            int cardCount = 0;
            int drawPileSize = player.drawPile.group.size();
            for (int i = 0; i < drawPileSize; i++)
            {
                AbstractCard drawPileCard = player.drawPile.group.get((drawPileSize - i) - 1);
                
                if (i >= numCardsToSearch)
                {
                    //stop looking after numCardsToSearch cards
                    break;
                }
                
                if (drawPileCard.costForTurn == 0)
                {
                    zeroCostCards.group.add(drawPileCard);
                }
            }
            
            while (!zeroCostCards.isEmpty())
            {
                AbstractCard topCard = zeroCostCards.getTopCard();
                zeroCostCards.removeTopCard();
                
                addToBot(new MoveCardToHandAction(topCard));
            }
            
            isDone = true;
            //endregion
        }
    }
}
