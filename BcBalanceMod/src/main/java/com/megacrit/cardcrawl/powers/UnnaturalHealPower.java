package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

public class UnnaturalHealPower extends BcPowerBase
{
    public static final String POWER_ID = "UnnaturalHealPower";
    
    public UnnaturalHealPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Unnatural Heal";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "unnaturalHeal32x32.png";
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
        return "At the end of combat, NL heal for #b" + amount + ".";
    }
    //endregion
    
    public void onVictory()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.currentHealth > 0)
        {
            player.heal(amount);
            addToTop(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}
