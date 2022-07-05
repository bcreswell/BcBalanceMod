package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawCardAction extends AbstractGameAction
{
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList();
    private boolean clearDrawHistory;
    private AbstractGameAction followUpAction;
    
    public DrawCardAction(AbstractCreature source, int amount, boolean endTurnDraw)
    {
        shuffleCheck = false;
        clearDrawHistory = true;
        followUpAction = null;
        if (endTurnDraw)
        {
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        }
        
        setValues(AbstractDungeon.player, source, amount);
        actionType = AbstractGameAction.ActionType.DRAW;
        if (Settings.FAST_MODE)
        {
            duration = Settings.ACTION_DUR_XFAST;
        }
        else
        {
            duration = Settings.ACTION_DUR_FASTER;
        }
        
    }
    
    public DrawCardAction(AbstractCreature source, int amount)
    {
        this(source, amount, false);
    }
    
    public DrawCardAction(int amount, boolean clearDrawHistory)
    {
        this(amount);
        this.clearDrawHistory = clearDrawHistory;
    }
    
    public DrawCardAction(int amount)
    {
        this(null, amount, false);
    }
    
    public DrawCardAction(int amount, AbstractGameAction action)
    {
        this(amount, action, true);
    }
    
    public DrawCardAction(int amount, AbstractGameAction action, boolean clearDrawHistory)
    {
        this(amount, clearDrawHistory);
        followUpAction = action;
    }
    
    public void update()
    {
        if (clearDrawHistory)
        {
            clearDrawHistory = false;
            drawnCards.clear();
        }
        
        if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID))
        {
            AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
            endActionWithFollowUp();
        }
        else if (amount <= 0)
        {
            endActionWithFollowUp();
        }
        else
        {
            int drawPileSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            int handSize = AbstractDungeon.player.hand.size();
            if (!SoulGroup.isActive())
            {
                if (drawPileSize + discardSize == 0)
                {
                    endActionWithFollowUp();
                }
                else if (AbstractDungeon.player.hand.size() == 10)
                {
                    AbstractDungeon.player.createHandIsFullDialog();
                    endActionWithFollowUp();
                }
                else
                {
                    if (!shuffleCheck)
                    {
                        if (amount + handSize > 10)
                        {
                            //reduce draw amount to be the remaining space in the hand and notify the player
                            amount = 10 - handSize;
                            //AbstractDungeon.player.createHandIsFullDialog();
                        }
                        
                        if (amount > drawPileSize)
                        {
                            int additionalDrawAfterShuffle = amount - drawPileSize;
                            amount = drawPileSize;
                            
                            //dont try to shuffle unless there's actually something in the discard pile. This check fixes the shuffle bug.
                            if (discardSize > 0)
                            {
                                //the drawing needs to be split like this so that the rest of the drawing will reduce the Dizzy stacks.
                                // otherwise they wouldn't be applied until we were done w/ drawing with this action.
                                addToTop(new DrawCardAction(additionalDrawAfterShuffle, followUpAction, false));
                                addToTop(new EmptyDeckShuffleAction());
                                addToTop(new DrawCardAction(amount, null, false));
                                
                                //dont do follow up after this action since its been handed off to the subsequent draw action now.
                                followUpAction = null;
                                isDone = true;
                                amount = 0;
                                return;
                            }
                            //else -> it just continues this action and draws the remaining cards in the draw pile
                        }
                        
                        shuffleCheck = true;
                    }
                    
                    duration -= Gdx.graphics.getDeltaTime();
                    if ((amount > 0) && (duration < 0.0F))
                    {
                        if (Settings.FAST_MODE)
                        {
                            duration = Settings.ACTION_DUR_XFAST;
                        }
                        else
                        {
                            duration = Settings.ACTION_DUR_FASTER;
                        }
                        
                        amount--;
                        if (!AbstractDungeon.player.drawPile.isEmpty())
                        {
                            drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        }
                        else
                        {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            endActionWithFollowUp();
                        }
                        
                        if (amount == 0)
                        {
                            endActionWithFollowUp();
                        }
                    }
                }
            }
        }
    }
    
    private void endActionWithFollowUp()
    {
        isDone = true;
        if (followUpAction != null)
        {
            addToTop(followUpAction);
        }
        
    }
}
