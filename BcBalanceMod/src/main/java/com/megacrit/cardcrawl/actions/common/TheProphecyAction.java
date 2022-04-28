//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;

public class TheProphecyAction extends AbstractGameAction
{
    CardGroup allCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    
    public TheProphecyAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.isDone = true;
        }
        else if (this.duration == Settings.ACTION_DUR_FAST)
        {
            allCards.group.addAll(player.hand.group);
            allCards.group.addAll(player.drawPile.group);
            allCards.group.addAll(player.discardPile.group);
            allCards.shuffle();
            
            if (allCards.size() > 0)
            {
                AbstractDungeon.gridSelectScreen.open(allCards, 1, "Marked by Fate", false);
            }else
            {
                isDone = true;
            }
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard chosenCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
    
            TheProphecyPower theProphecyPower = new TheProphecyPower(player, 1);
            theProphecyPower.TheChosenCard = chosenCard;
            theProphecyPower.updateDescription();
            addToBot(new ApplyPowerAction(player,player,theProphecyPower));
    
            if (player.discardPile.size() + player.hand.size() > 0)
            {
                for (AbstractRelic r : AbstractDungeon.player.relics)
                {
                    r.onShuffle();
                }
    
                while (!player.hand.isEmpty())
                {
                    AbstractCard c = player.hand.getRandomCard(true);
                    player.hand.moveToDeck(c, true);
                }
                
                while (!player.discardPile.isEmpty())
                {
                    AbstractCard c = player.discardPile.getRandomCard(true);
                    player.discardPile.moveToDeck(c, true);
                }
            }
        }
        
        this.tickDuration();
    }
}
