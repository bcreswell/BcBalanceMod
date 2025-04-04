//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.baseCards.BcPowerBase;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcDevaPower extends BcPowerBase
{
    public static final String POWER_ID = "DevaForm";
    
    public BcDevaPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Deva Form";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "deva2";
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
        return "Start of Turn: Gain " + amount + " energy.";
    }
    //endregion
    
    public void onEnergyRecharge()
    {
        flash();
        AbstractDungeon.player.gainEnergy(amount);
    }
}