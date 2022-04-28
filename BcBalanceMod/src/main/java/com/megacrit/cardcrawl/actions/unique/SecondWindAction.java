package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class SecondWindAction extends AbstractGameAction
{
    private int blockPerCard;
    
    public SecondWindAction(int blockAmount)
    {
        blockPerCard = blockAmount;
        setValues(AbstractDungeon.player, AbstractDungeon.player);
        actionType = AbstractGameAction.ActionType.BLOCK;
    }
    
    public void update()
    {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList();
        
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if ((card.type == AbstractCard.CardType.SKILL) ||
                        (card.type == AbstractCard.CardType.POWER) ||
                        (card.type == AbstractCard.CardType.STATUS) ||
                        (card.type == AbstractCard.CardType.CURSE))
            {
                cardsToExhaust.add(card);
            }
        }
        
        for (AbstractCard card : cardsToExhaust)
        {
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockPerCard));
        }
        
        for (AbstractCard card : cardsToExhaust)
        {
            addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
        
        isDone = true;
    }
}
