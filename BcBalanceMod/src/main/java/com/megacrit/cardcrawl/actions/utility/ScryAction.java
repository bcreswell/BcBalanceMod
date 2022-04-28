package com.megacrit.cardcrawl.actions.utility;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

import java.util.Iterator;

public class ScryAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    CardGroup scryGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    int originalAmount;
    boolean dmgDone = false;
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ReprogramAction");
        TEXT = uiStrings.TEXT;
    }
    
    private float startingDuration;
    
    public ScryAction(int numCards)
    {
        amount = numCards;
        originalAmount = amount;
        if (AbstractDungeon.player.hasRelic("GoldenEye"))
        {
            AbstractDungeon.player.getRelic("GoldenEye").flash();
            amount += 2;
        }
        
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else
        {
            if (duration == startingDuration)
            {
                for (AbstractPower power : player.powers)
                {
                    power.onScry();
                }
                
                //scry can shuffle discard pile into draw pile if needed.
                if (amount > player.drawPile.size())
                {
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                    {
                        r.onShuffle();
                    }
                    
                    while (!player.discardPile.isEmpty())
                    {
                        AbstractCard card = player.discardPile.getRandomCard(true);
                        //'false' here moves the card to the top of the deck,
                        player.discardPile.moveToDeck(card, false);
                        // but should be bottom instead, so we gotta monkey with it
                        card = player.drawPile.group.remove(player.drawPile.group.size()  - 1);
                        player.drawPile.addToBottom(card);
                    }
                }
    
                amount = Math.min(amount, player.drawPile.size());
    
                for (int i = 0; i < amount; i++)
                {
                    AbstractCard card = player.drawPile.group.get((player.drawPile.group.size() - i) - 1);
                    scryGroup.addToBottom(card);
                }
                
                AbstractDungeon.gridSelectScreen.open(scryGroup, amount, true, TEXT[0]);
            }
            else
            {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                {
                    for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                    {
                        player.drawPile.moveToDiscardPile(card);
                    }
                    
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
    
                for (AbstractCard card : player.discardPile.group)
                {
                    card.triggerOnScry();
                }
                
                if (!dmgDone)
                {
                    dmgDone = true;
                    if (scryGroup.size() > 0)
                    {
                        int dmgTimes = BcUtility.getPowerAmount(TheFireWillSpreadPower.POWER_ID);
                        for (int i = 0; i < dmgTimes; i++)
                        {
                            //limited by the actual number of cards available to the scry action
                            addToBot(new TheFireWillSpreadAction(scryGroup.size()));
                        }
                    }
                }
            }
            
            tickDuration();
        }
    }
}
