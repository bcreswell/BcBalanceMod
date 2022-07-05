package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DiscardAtEndOfTurnActionNew extends AbstractGameAction
{
    public DiscardAtEndOfTurnActionNew()
    {
        duration = .1f;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        //note: intentionally not calling card.triggerOnEndOfPlayerTurn() here because all it did was exhaust ethereal cards.
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !player.isDead)
        {
            boolean hasRunicPyramid = player.hasRelic(RunicPyramid.ID);
            
            for (AbstractCard card : player.hand.group)
            {
                if (card.isEthereal)
                {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
                else
                {
                    if (card.retain || card.selfRetain)
                    {
                        card.onRetained();
                        card.retain = false;
                        
                    }
                    else if (hasRunicPyramid)
                    {
                        if ((card.type == AbstractCard.CardType.STATUS) || (card.type == AbstractCard.CardType.CURSE))
                        {
                            //runic pyramid now discards unplayable status and curse cards
                            if (card.cost == -2)
                            {
                                addToBot(new DiscardSpecificCardAction(card, null, true, true));
                            }
                        }
                    }
                    else
                    {
                        addToBot(new DiscardSpecificCardAction(card, null, true, true));
                    }
                }
            }
        }
        
        isDone = true;
    }
}
