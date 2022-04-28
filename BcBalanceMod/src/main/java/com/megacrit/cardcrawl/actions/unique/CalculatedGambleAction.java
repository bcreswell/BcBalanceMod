package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.*;

public class CalculatedGambleAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean isUpgraded;
    boolean firstUpdate = true;
    
    public CalculatedGambleAction(boolean upgraded)
    {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
        this.isUpgraded = upgraded;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (firstUpdate)
        {
            firstUpdate = false;
            
            if (player.hand.size() == 0)
            {
                isDone = true;
                return;
            }
            
            int count = AbstractDungeon.player.hand.size();
            
            if (!isUpgraded)
            {
                addToTop(new DrawCardAction(target, count));
                addToTop(new DiscardAction(target, target, count, true));
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 10, true, true);
                tickDuration();
            }
        }
        else if (isUpgraded && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int discardCount = AbstractDungeon.handCardSelectScreen.selectedCards.size();
            if (discardCount > 0)
            {
                addToBot(new DrawCardAction(player, discardCount));
                
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