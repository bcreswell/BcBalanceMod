package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RetrieveRandomCardsAction extends AbstractGameAction
{
    public RetrieveRandomCardsAction(int amount)
    {
        this.amount = amount;
    }
    
    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        CardGroup possibleRetrieves = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard discardPileCard : player.discardPile.group)
        {
            if (BcUtility.canBeRetrieved(discardPileCard))
            {
                possibleRetrieves.addToBottom(discardPileCard);
            }
        }
        
        for (int i = 0; i < amount; i++)
        {
            if ((possibleRetrieves.size() > 0) && (player.hand.size() < 10))
            {
                AbstractCard cardToRetrieve = possibleRetrieves.getRandomCard(true);
                possibleRetrieves.removeCard(cardToRetrieve);
                player.discardPile.moveToHand(cardToRetrieve, player.discardPile);
                cardToRetrieve.current_x = CardGroup.DISCARD_PILE_X;
            }
        }
        
        isDone = true;
    }
}
