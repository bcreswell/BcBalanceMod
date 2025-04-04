package com.megacrit.cardcrawl.powers;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SerpentSkinPower extends BcPowerBase
{
    public static final String POWER_ID = "SerpentSkinPower";
    
    public SerpentSkinPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Serpent Skin";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "swivel";
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
        if (amount == 0)
        {
            return "When you Discard: Shed a stack of either Vulnerable, Frail or Weak.";
        }
        else
        {
            return "When you Discard: Shed a stack of either Vulnerable, Frail or Weak "+amount+" times.";
        }
    }
    //endregion
    
    @Override
    public void onManualDiscard()
    {
        AbstractPower vuln = player.getPower(VulnerablePower.POWER_ID);
        AbstractPower weak = player.getPower(WeakPower.POWER_ID);
        AbstractPower frail = player.getPower(FrailPower.POWER_ID);
        
        int debuffCount = 0;
        if (vuln != null)
        {
            debuffCount += vuln.amount;
        }
        
        if (weak != null)
        {
            debuffCount += weak.amount;
        }
        
        if (frail != null)
        {
            debuffCount += frail.amount;
        }
        
        int count = Math.min(amount, debuffCount);
        
        for(int i = 0; i < count; i++)
        {
            addToBot(new ShedDebuffAction(player, player));
        }
    }
}
