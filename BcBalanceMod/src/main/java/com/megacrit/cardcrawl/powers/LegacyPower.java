package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class LegacyPower extends BcPowerBase
{
    public static final String POWER_ID = "LegacyPower";
    
    public LegacyPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
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
        if (amount == 1)
        {
            return "Whenever you gain Mantra, Draw a card.";
        }
        else
        {
            return "Whenever you gain Mantra, Draw " + amount + " cards.";
        }
    }
    //endregion
    
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((target == AbstractDungeon.player) && (power.ID.equals(MantraPower.POWER_ID)))
        {
            flash();
            addToBot(new DrawCardAction(amount));
        }
    }
}
