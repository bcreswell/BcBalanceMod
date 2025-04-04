package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class DiscardAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean endTurn;
    public static int numDiscarded;
    private static final float DURATION;
    
    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom)
    {
        this(target, source, amount, isRandom, false);
    }
    
    public DiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn)
    {
        this.p = (AbstractPlayer) target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
        this.endTurn = endTurn;
        this.duration = DURATION;
    }
    
    public void update()
    {        
        if (duration == DURATION)
        {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                isDone = true;
                return;
            }
            
            if (p.hand.size() <= amount)
            {
                amount = p.hand.size();
                int handSize = p.hand.size();
                
                for (int i = 0; i < handSize; ++i)
                {
                    AbstractCard c = p.hand.getTopCard();
                    if (BcUtility.isCardTemporary(c))
                    {
                        p.hand.moveToExhaustPile(c);
                    }
                    else
                    {
                        p.hand.moveToDiscardPile(c);
                        if (!endTurn)
                        {
                            c.triggerOnManualDiscard();
                        }
                        
                        GameActionManager.incrementDiscard(endTurn);
                    }
                }
                
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
            
            if (!isRandom)
            {
                if (amount < 0)
                {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    tickDuration();
                    return;
                }
                
                numDiscarded = amount;
                if (p.hand.size() > amount)
                {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
                }
                
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
            
            for (int i = 0; i < amount; ++i)
            {
                AbstractCard c = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                if (BcUtility.isCardTemporary(c))
                {
                    p.hand.moveToExhaustPile(c);
                }
                else
                {
                    p.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(endTurn);
                }
            }
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            
            while (var4.hasNext())
            {
                AbstractCard c = (AbstractCard) var4.next();
                if (BcUtility.isCardTemporary(c))
                {
                    p.hand.moveToExhaustPile(c);
                }
                else
                {
                    p.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(endTurn);
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        
        tickDuration();
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}