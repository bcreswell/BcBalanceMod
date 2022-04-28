package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.*;

import java.util.Iterator;

public class GainEnergyAction extends AbstractGameAction
{
    private int energyGain;
    
    public GainEnergyAction(int amount)
    {
        setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        duration = Settings.ACTION_DUR_FAST;
        energyGain = amount;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            int newEnergyAmount = energyGain + EnergyPanel.totalCount;
            if (newEnergyAmount < 0)
            {
                energyGain -= newEnergyAmount;
            }
            
            if (energyGain != 0)
            {
                AbstractDungeon.player.gainEnergy(energyGain);
                AbstractDungeon.actionManager.updateEnergyGain(energyGain);
                Iterator var1 = AbstractDungeon.player.hand.group.iterator();
                
                while (var1.hasNext())
                {
                    AbstractCard c = (AbstractCard) var1.next();
                    c.triggerOnGainEnergy(energyGain, true);
                }
            }
        }
        
        tickDuration();
    }
}
