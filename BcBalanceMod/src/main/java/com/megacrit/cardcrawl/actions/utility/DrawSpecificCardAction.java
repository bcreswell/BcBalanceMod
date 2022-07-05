package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DrawSpecificCardAction extends AbstractGameAction
{
    AbstractCard cardToDraw;
    
    public DrawSpecificCardAction(AbstractCard cardToDraw)
    {
        this.cardToDraw = cardToDraw;
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_MED;
        startDuration = duration;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            if (!player.drawPile.isEmpty() &&
                        (player.hand.size() < 10) &&
                        player.drawPile.contains(cardToDraw))
            {
                cardToDraw.unhover();
                cardToDraw.lighten(true);
                cardToDraw.setAngle(0.0F);
                cardToDraw.drawScale = 0.12F;
                cardToDraw.targetDrawScale = 0.75F;
                cardToDraw.current_x = CardGroup.DRAW_PILE_X;
                cardToDraw.current_y = CardGroup.DRAW_PILE_Y;
                
                player.drawPile.removeCard(cardToDraw);
                player.hand.addToTop(cardToDraw);
                player.hand.refreshHandLayout();
                player.hand.applyPowers();
                player.hand.glowCheck();
            }
            else
            {
                isDone = true;
            }
        }
        
        tickDuration();
    }
}
