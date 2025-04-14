package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class OmniscienceAction extends AbstractGameAction
{
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int playAmt;
    private boolean isUpgraded;
    CardGroup allCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    
    public OmniscienceAction(int numberOfCards)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        playAmt = numberOfCards;
    }
    public OmniscienceAction(int numberOfCards, boolean isUpgraded)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        playAmt = numberOfCards;
        this.isUpgraded = isUpgraded;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            allCards.group.addAll(player.drawPile.group);
            if (isUpgraded)
            {
                allCards.group.addAll(player.hand.group);
                allCards.group.addAll(player.discardPile.group);
            }
            allCards.sortAlphabetically(true);
            allCards.sortByRarity(false);
            
            for (int i = 0; i < allCards.group.size(); i++)
            {
                AbstractCard card = allCards.group.get(i);
                card.isGlowing = false;
                card.unhover();
                card.setAngle(0.0F, true);
                card.lighten(false);
                
                //can't omni another omni.
                if (card.cardID.equals(Omniscience.ID))
                {
                    allCards.group.remove(i);
                    i--;
                }
            }
            
            if (allCards.size() > 0)
            {
                AbstractDungeon.gridSelectScreen.open(allCards, 1, "For Science!", false);
            }
            else
            {
                isDone = true;
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    if (AbstractDungeon.player.drawPile.group.contains(c))
                    {
                        AbstractDungeon.player.drawPile.group.remove(c);
                    }
                    
                    if (isUpgraded)
                    {
                        if (AbstractDungeon.player.discardPile.group.contains(c))
                        {
                            AbstractDungeon.player.discardPile.group.remove(c);
                        }
                        
                        if (AbstractDungeon.player.hand.group.contains(c))
                        {
                            //bugs out if you try to remove from hand directly. But you can omni -> omni same card if its in hand.
                            AbstractDungeon.player.hand.moveToDeck(c, true);
                            AbstractDungeon.player.drawPile.group.remove(c);
                        }
                    }
                    
                    c.exhaust = true;
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    
                    addToBot(new NewQueueCardAction(c, true, false, true));
                    
                    for (int i = 0; i < playAmt - 1; i++)
                    {
                        AbstractCard tmp = c.makeStatEquivalentCopy();
                        tmp.purgeOnUse = true;
                        addToBot(new NewQueueCardAction(tmp, true, false, true));
                    }
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }
        }
        tickDuration();
    }
    
    static
    {
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT;
    }
}
