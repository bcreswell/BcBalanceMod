package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;

public class GhostlyBlockPower extends BcPowerBase
{
    public static final String POWER_ID = "GhostlyBlock";
    
    public GhostlyBlockPower(AbstractCreature owner, int blockAmt)
    {
        super(owner, blockAmt);
        priority = 75;
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Ghostly Block";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "ghostlyBlock32x32.png";
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
        return "When you Gain Block while intangible, it becomes Ghostly Block instead. Ghostly Block doesn't protect you but Body Slam, Entrench, Juggernaut, Barricade, etc. still work by using this Ghostly Block instead of the normal kind.";
    }
    //endregion
    
    public void atStartOfTurn()
    {
        amount -= BcUtility.getBlockToLose(amount);
        
        if (amount <= 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}
