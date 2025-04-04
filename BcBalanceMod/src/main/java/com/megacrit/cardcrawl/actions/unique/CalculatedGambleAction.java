package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;

public class CalculatedGambleAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean chooseDiscards;
    boolean firstUpdate = true;
    int extraDraw;
    
    public CalculatedGambleAction(boolean chooseDiscards, int extraDraw)
    {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
        this.chooseDiscards = chooseDiscards;
        this.extraDraw = extraDraw;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (firstUpdate)
        {
            firstUpdate = false;
            
            int handSize = player.hand.size();
            
            if (!chooseDiscards)
            {
                addToTop(new DrawCardAction(target, handSize + extraDraw));
                
                if (handSize > 0)
                {
                    addToTop(new DiscardAction(target, target, handSize, true));
                }
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 10, true, true);
                tickDuration();
            }
        }
        else if (chooseDiscards && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int discardCount = AbstractDungeon.handCardSelectScreen.selectedCards.size();
            if (discardCount > 0)
            {
                addToBot(new DrawCardAction(player, discardCount+extraDraw));
                
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(card);
                    GameActionManager.incrementDiscard(false);
                    card.triggerOnManualDiscard();
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        else
        {
            tickDuration();
        }
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }
}