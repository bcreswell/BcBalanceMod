package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DependencyInversionDrawAction extends AbstractGameAction
{
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList();
    
    public DependencyInversionDrawAction(AbstractCreature source, int amount)
    {
        this.shuffleCheck = false;
        
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.DRAW;
        if (Settings.FAST_MODE)
        {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else
        {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    
    public void update()
    {
        AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        if ((focus == null) || (focus.amount >= 0))
        {
            this.isDone = true;
            return;
        }
        
        if (AbstractDungeon.player.hasPower("No Draw"))
        {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
        }
        else if (this.amount <= 0)
        {
            this.isDone = true;
        }
        else
        {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive())
            {
                if (deckSize + discardSize == 0)
                {
                    this.isDone = true;
                }
                else if (AbstractDungeon.player.hand.size() == 10)
                {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                }
                else
                {
                    if (!this.shuffleCheck)
                    {
                        int tmp;
                        if (this.amount + AbstractDungeon.player.hand.size() > 10)
                        {
                            tmp = 10 - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += tmp;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }
                        
                        if (this.amount > deckSize)
                        {
                            tmp = this.amount - deckSize;
                            this.addToTop(new EmptyDeckShuffleAction());
                            if (deckSize != 0)
                            {
                                this.addToTop(new DrawCardAction(deckSize, false));
                            }
                            
                            this.amount = 0;
                            this.isDone = true;
                            return;
                        }
                        
                        this.shuffleCheck = true;
                    }
                    
                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F)
                    {
                        if (Settings.FAST_MODE)
                        {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        }
                        else
                        {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }
                        
                        --this.amount;
                        if (!AbstractDungeon.player.drawPile.isEmpty())
                        {
                            drawnCards.add(AbstractDungeon.player.drawPile.getTopCard());
                            AbstractDungeon.player.draw();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        }
                        else
                        {
                            logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.isDone = true;
                        }
                        
                        if (this.amount == 0)
                        {
                            this.isDone = true;
                        }
                    }
                    
                }
            }
        }
    }
}
