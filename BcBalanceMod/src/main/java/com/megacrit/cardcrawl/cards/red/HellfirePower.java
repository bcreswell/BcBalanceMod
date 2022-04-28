package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;

public class HellfirePower extends BcPowerBase
{
    public static final String POWER_ID = "HellfirePower";
    String idOffset;
    
    public HellfirePower(String idOffset, int amount)
    {
        super(null, amount);
        this.idOffset = idOffset;
        this.ID += idOffset;
        updateDescription();
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Hellfire";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "hellfire35x35.png";
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
        if (amount >= Combust.CombustThreshold)
        {
            return "Start of turn: If this is your biggest Hellfire, it will be consumed dealing " + amount + " damage to ALL enemies.";
        }
        else
        {
            return "Hellfire grows as you lose HP. If you have at least 10 at the start of your turn, it is consumed to deal that much damage to ALL enemies.";
        }
    }
    //endregion
}
