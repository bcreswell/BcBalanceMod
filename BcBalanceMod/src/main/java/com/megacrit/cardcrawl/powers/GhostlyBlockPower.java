package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
        return "Your block doesn't protect you while you're Intangible.";
    }
    //endregion
    
    public void atStartOfTurn()
    {
        //region reduce block if applicable
        if (!owner.hasPower(BarricadePower.POWER_ID) && !owner.hasPower(BlurPower.POWER_ID))
        {
            int blockToRetain = 0;
            if ((owner == player) && player.hasRelic(Calipers.ID))
            {
                blockToRetain = Calipers.BLOCK_LOSS;
            }
            
            if (blockToRetain == 0)
            {
                amount = 0;
            }
            else
            {
                amount -= blockToRetain;
                if (amount <= 0)
                {
                    amount = 0;
                }
            }
        }
        //endregion
        
        if (amount == 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}
