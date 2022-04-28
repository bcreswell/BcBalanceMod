package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DiscardAtEndOfTurnAction extends AbstractGameAction
{
    private static final float DURATION;
    
    public DiscardAtEndOfTurnAction()
    {
        this.duration = DURATION;
    }
    
    public void update()
    {
        if (this.duration == DURATION)
        {
            Iterator handIterator = AbstractDungeon.player.hand.group.iterator();
            
            boolean hasRunicPyramid = AbstractDungeon.player.hasRelic("Runic Pyramid");
            boolean isEquilibriumActive = AbstractDungeon.player.hasPower("Equilibrium");
            AbstractPlayer player = AbstractDungeon.player;
            
            while (true)
            {
                AbstractCard e;
                do
                {
                    if (!handIterator.hasNext())
                    {
                        this.addToTop(new RestoreRetainedCardsAction(AbstractDungeon.player.limbo));
                        if (!hasRunicPyramid && !isEquilibriumActive)
                        {
                            int tempSize = AbstractDungeon.player.hand.size();
                            
                            for (int i = 0; i < tempSize; ++i)
                            {
                                this.addToTop(new DiscardAction(AbstractDungeon.player, (AbstractCreature) null, AbstractDungeon.player.hand.size(), true, true));
                            }
                        }
                        else if (hasRunicPyramid && !isEquilibriumActive)
                        {
                            //runic pyramid logic
                            int tempSize = AbstractDungeon.player.hand.size();
    
                            for (int i = 0; i < tempSize; ++i)
                            {
                                AbstractCard cardToDiscard = player.hand.group.get(i);
                                
                                //with runic pyramid, only discard unplayable status and curse cards
                                if ((cardToDiscard.type == AbstractCard.CardType.STATUS) || (cardToDiscard.type == AbstractCard.CardType.CURSE))
                                {
                                    if (cardToDiscard.cost == -2)
                                    {
                                        addToTop(new DiscardSpecificCardAction(cardToDiscard));
                                    }
                                }
                            }
                        }

                        ArrayList<AbstractCard> cards = (ArrayList) AbstractDungeon.player.hand.group.clone();
                        Collections.shuffle(cards);
                        Iterator var7 = cards.iterator();
                        
                        while (var7.hasNext())
                        {
                            AbstractCard card = (AbstractCard) var7.next();
                            card.triggerOnEndOfPlayerTurn();
                        }
                        
                        this.isDone = true;
                        return;
                    }
                    
                    e = (AbstractCard) handIterator.next();
                } while (!e.retain && !e.selfRetain);
                
                AbstractDungeon.player.limbo.addToTop(e);
                handIterator.remove();
            }
        }
    }
    
    static
    {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
