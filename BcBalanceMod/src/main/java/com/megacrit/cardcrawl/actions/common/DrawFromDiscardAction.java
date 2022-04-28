package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawFromDiscardAction extends AbstractGameAction
{
    
    public DrawFromDiscardAction()
    {
    }
    
    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if ((player.discardPile.size() > 0) && (player.hand.size() < 10))
        {
            AbstractCard cardToDraw = player.discardPile.getRandomCard(true);
            player.discardPile.moveToHand(cardToDraw, player.discardPile);
            cardToDraw.current_x = CardGroup.DISCARD_PILE_X;
        }
        
        isDone = true;
    }
}
