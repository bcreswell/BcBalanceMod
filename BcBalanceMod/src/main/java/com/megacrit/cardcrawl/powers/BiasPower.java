//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BiasPower extends BcPowerBase
{
    public static final String POWER_ID = "Bias";
    
    public BiasPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Bias";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "bias";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.DEBUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Lose 1 Focus per turn for the next " + amount + " turns.";
    }
    //endregion
    
    public void atStartOfTurn()
    {
        flash();
        
        if (amount > 0)
        {
            amount--;
            addToBot(new BcApplyPowerAction(new FocusPower(owner, -1)));
        }
        
        if (amount == 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
        
//        int currentFocus = getCurrentFocusAmount();
//        if (currentFocus > 0)
//        {
//            int toRemove = Math.min(currentFocus, amount);
//            addToBot(new BcApplyPowerAction(new FocusPower(owner, -toRemove)));
//
//            currentFocus -= toRemove;
//        }
//
//        if (currentFocus <= 0)
//        {
//            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
//        }
    }
}
