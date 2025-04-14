//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class RecycleAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public boolean isUpgraded;
    
    public RecycleAction()
    {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (player.hand.isEmpty())
            {
                isDone = true;
            }
            else if (player.hand.size() == 1)
            {
                gainEnergyFromCard(player.hand.getBottomCard());
                
                player.hand.moveToExhaustPile(player.hand.getBottomCard());
                tickDuration();
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    player.hand.moveToExhaustPile(c);
                    gainEnergyFromCard(c);
                }
                
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            
            tickDuration();
        }
    }
    
    void gainEnergyFromCard(AbstractCard cardToRecycle)
    {
        int energyToGain = 0;
        
        if (cardToRecycle.costForTurn == -1)
        {
            energyToGain = EnergyPanel.getCurrentEnergy();
        
//            if (!isUpgraded)
//            {
//                energyToGain = EnergyPanel.getCurrentEnergy() - 1;
//            }
        }
        else if (cardToRecycle.costForTurn > 0)
        {
            energyToGain = cardToRecycle.costForTurn;
//            if (!isUpgraded)
//            {
//                energyToGain = cardToRecycle.costForTurn - 1;
//            }
        }
        
        if (energyToGain > 0)
        {
            addToTop(new GainEnergyAction(energyToGain));
            
            if (isUpgraded)
            {
                addToBot(new DrawCardAction(1));
            }
        }
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
        TEXT = uiStrings.TEXT;
    }
}
