package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.green.WellLaidPlans;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class ThinkingAheadPower extends BcPowerBase
{
    public static final String POWER_ID = "ThinkingAheadPower";
    
    public ThinkingAheadPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Thinking Ahead";
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
            return "At the beginning of next turn, #b"+amount+" stack of \"Retain Cards\" will be removed because it is temporary.";
        }
        else
        {
            return "At the beginning of next turn, #b"+amount+" stacks of \"Retain Cards\" will be removed because they are temporary.";
        }
    }
    //endregion
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            addToBot(new ReducePowerAction(player, player, RetainCardPower.POWER_ID, amount));
            addToBot(new RemoveSpecificPowerAction(player, player, this));
        }
    }
}
