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
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        startDuration = duration;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else if (duration == startDuration) //first update
        {
            allCards.group.addAll(player.hand.group);
            allCards.group.addAll(player.drawPile.group);
            allCards.group.addAll(player.discardPile.group);
            allCards.sortAlphabetically(true);
            
            for (AbstractCard card : allCards.group)
            {
                card.isGlowing = false;
                card.unhover();
                card.setAngle(0.0F, true);
                card.lighten(false);
            }
            
            if (allCards.size() > 0)
            {
                AbstractDungeon.gridSelectScreen.open(allCards, 1, "Fates Entangled", false);
            }
            else
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
            addToBot(new BcApplyPowerAction(theProphecyPower));
            
//            if (player.discardPile.size() + player.hand.size() > 0)
//            {
//                for (AbstractRelic r : AbstractDungeon.player.relics)
//                {
//                    r.onShuffle();
//                }
//
//                while (!player.hand.isEmpty())
//                {
//                    AbstractCard c = player.hand.getRandomCard(true);
//                    player.hand.moveToDeck(c, true);
//                }
//
//                while (!player.discardPile.isEmpty())
//                {
//                    AbstractCard c = player.discardPile.getRandomCard(true);
//                    player.discardPile.moveToDeck(c, true);
//                }
//            }
        }
        
        tickDuration();
    }
}
