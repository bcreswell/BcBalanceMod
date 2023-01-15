package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

public class MoveCardToHandAction extends AbstractGameAction
{
    AbstractCard cardToMove;
    boolean skipIfNoRoom;
    
    public MoveCardToHandAction(AbstractCard cardToMove, boolean skipIfNoRoom)
    {
        this.cardToMove = cardToMove;
        this.skipIfNoRoom = skipIfNoRoom;
        duration = 0;
    }
    
    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        cardToMove.unhover();
        
        if (player.drawPile.contains(cardToMove))
        {
            AbstractPower noDrawPower = player.getPower(NoDrawPower.POWER_ID);
            if (noDrawPower != null)
            {
                noDrawPower.flash();
            }
            else
            {
                if (skipIfNoRoom && (player.hand.size() >= 10))
                {
                    //no room in hand, so skip this action
                }
                else
                {
                    cardToMove.triggerWhenDrawn();
        
                    if (player.hand.size() == 10)
                    {
                        player.drawPile.moveToDiscardPile(cardToMove);
                        player.createHandIsFullDialog();
                    }
                    else
                    {
                        player.drawPile.moveToHand(cardToMove, player.drawPile);
                    }
        
                    for (AbstractPower power : player.powers)
                    {
                        power.onCardDraw(cardToMove);
                    }
        
                    for (AbstractRelic relic : player.relics)
                    {
                        relic.onCardDraw(cardToMove);
                    }
                }
            }
        }
        else if (player.discardPile.contains(cardToMove))
        {
            if (player.hand.size() == 10)
            {
                player.createHandIsFullDialog();
            }
            else
            {
                player.discardPile.moveToHand(cardToMove, player.discardPile);
            }
        }
        else if (player.exhaustPile.contains(cardToMove))
        {
            if (player.hand.size() == 10)
            {
                player.exhaustPile.moveToDiscardPile(cardToMove);
                player.createHandIsFullDialog();
            }
            else
            {
                player.exhaustPile.moveToHand(cardToMove, player.exhaustPile);
            }
        }
        
        player.hand.refreshHandLayout();
        player.hand.glowCheck();
        player.hand.applyPowers();
        
        isDone = true;
    }
}
