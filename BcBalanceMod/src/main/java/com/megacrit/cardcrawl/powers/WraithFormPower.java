package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class WraithFormPower extends BcPowerBase
{
    public static final String POWER_ID = "Wraith Form v2";
    public static final int IntangibleFrequency = 3;
    
    public WraithFormPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Wraith Form";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "wraithForm";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Unique;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Become Intangible every 3rd turn for the rest of combat.";
    }
    //endregion
    
    public void atEndOfTurn(boolean isPlayer)
    {
    }
    
    @Override
    public void onInitialApplication()
    {
        amount = 3;
        addToBot(new BcApplyPowerAction(new IntangiblePlayerPower(player, 1)));
    }
    
    @Override
    public void atStartOfTurn()
    {
        amount = (amount % IntangibleFrequency) + 1;
        
        if (amount == IntangibleFrequency)
        {
            addToBot(new BcApplyPowerAction(new IntangiblePlayerPower(player, 1)));
        }
        
        updateDescription();
    }
    
    @Override
    public void stackPower(int stackAmount)
    {
        amount = IntangibleFrequency;
        addToBot(new BcApplyPowerAction(new IntangiblePlayerPower(player, 1)));
        
        if (amount != 0)
        {
            fontScale = 8.0F;
        }
        
        updateDescription();
    }
}
