package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class LegacyPower extends BcPowerBase
{
    public static final String POWER_ID = "LegacyPower";
    int mantraThreshold;
    
    public LegacyPower(AbstractCreature owner, int amount, int mantraThreshold)
    {
        super(owner, amount);
        this.mantraThreshold = mantraThreshold;
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Legacy";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "unawakened";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (mantraThreshold == 1)
        {
            if (amount == 1)
            {
                return "Whenever you gain Mantra, Draw a card.";
            }
            else
            {
                return "Whenever you gain Mantra, Draw " + amount + " cards.";
            }
        }
        else
        {
            if (amount == 1)
            {
                return "Whenever you gain #b" + mantraThreshold + " or more Mantra, Draw a card.";
            }
            else
            {
                return "Whenever you gain #b" + mantraThreshold + " or more Mantra, Draw " + amount + " cards.";
            }
        }
    }
    //endregion
    
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((target == AbstractDungeon.player) &&
            (power.ID.equals(MantraPower.POWER_ID)) &&
            (power.amount >= mantraThreshold))
        {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }
}
