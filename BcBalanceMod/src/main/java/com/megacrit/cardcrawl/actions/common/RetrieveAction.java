package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetrieveAction extends AbstractGameAction
{
    boolean retain;
    int retrieveCount;
    int minCost;
    String excludedCardId;
    
    public RetrieveAction()
    {
        this(1, false, 1, null);
    }
    public RetrieveAction(int count, boolean retain, int minCost, String excludedCardId)
    {
        retrieveCount = count;
        this.retain = retain;
        this.minCost = minCost;
        this.excludedCardId = excludedCardId;
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (duration == startDuration) //first update
        {
            CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard discardPileCard : player.discardPile.group)
            {
                if (BcUtility.canBeRetrieved(discardPileCard) &&
                    ((excludedCardId == null) || (discardPileCard.cardID != excludedCardId)))
                {
                    if((minCost == 0) || (discardPileCard.cost >= minCost))
                    {
                        possibleCards.addToBottom(discardPileCard);
                    }
                }
            }
            
            possibleCards.sortAlphabetically(true);
            possibleCards.sortByRarity(false);
            
            if (!possibleCards.isEmpty())
            {
                if (possibleCards.size() <= retrieveCount)
                {
                    //region retrieve immediately without popup
                    AbstractCard card = possibleCards.group.get(0);
                    if (retain)
                    {
                        card.retain = true;
                    }
                    player.hand.addToHand(card);
                    player.discardPile.removeCard(card);
                    card.lighten(false);
                    card.applyPowers();
                    
                    isDone = true;
                    //endregion
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(possibleCards, retrieveCount, "Retrieve "+BcUtility.getCardCountString(retrieveCount), false);
                    
                    tickDuration();
                }
            }
            else
            {
                isDone = true;
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                boolean wasZeroCost = false;
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    player.hand.addToHand(card);
                    player.discardPile.removeCard(card);
                    card.lighten(false);
                    card.unhover();
                    card.applyPowers();
                    if (retain)
                    {
                        card.retain = true;
                    }
                }
                
                for (AbstractCard card : player.discardPile.group)
                {
                    card.target_y = 0.0F;
                    card.unhover();
                    card.target_x = CardGroup.DISCARD_PILE_X;
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            
            tickDuration();
            if (isDone)
            {
                for (AbstractCard card : player.hand.group)
                {
                    card.applyPowers();
                }
            }
        }
    }
}
