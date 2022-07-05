package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BufferPower extends BcPowerBase
{
    public static final String POWER_ID = "Buffer";
    
    public BufferPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Buffer";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "buffer";
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
            return "Prevent the next time you would lose HP. NL ( Disabled while #yIntangible. )";
        }
        else
        {
            return "Prevent the next #b" + amount + " times you would lose HP. NL ( Disabled while #yIntangible. )";
        }
    }
    //endregion
    
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        //just too much anti-synergy between intangible and buffer otherwise.
        if (!owner.hasPower(IntangiblePlayerPower.POWER_ID))
        {
            if (damageAmount > 0)
            {
                addToTop(new ReducePowerAction(owner, owner, ID, 1));
            }
            
            return 0;
        }
        else
        {
            return damageAmount;
        }
    }
}
