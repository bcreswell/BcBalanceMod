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

public class HologramAction extends AbstractGameAction
{
    public HologramAction()
    {
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
                if (!BcCardBase.isARetrieveCard(discardPileCard))
                {
                    possibleCards.addToBottom(discardPileCard);
                }
            }
            
            possibleCards.sortAlphabetically(true);
            possibleCards.sortByRarity(false);
            
            if (!possibleCards.isEmpty())
            {
                if (possibleCards.size() == 1)
                {
                    //region retrieve immediately without popup
                    AbstractCard card = possibleCards.group.get(0);
                    player.hand.addToHand(card);
                    player.discardPile.removeCard(card);
                    card.lighten(false);
                    card.applyPowers();
                    
                    isDone = true;
                    //endregion
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(possibleCards, 1, "Retrieve a card", false);
                    
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
