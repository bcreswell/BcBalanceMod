package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class UnnaturalRegenerationPower extends BcPowerBase
{
    public static final String POWER_ID = "UnnaturalRegenerationPower";
    
    public UnnaturalRegenerationPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Unnatural Regeneration";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "unnaturalRegeneration32x32.png";
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
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Unique;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When you lose HP from a card, heal back that amount at the end of combat. NL ( Doesn't stack. )";
    }
    //endregion
    
    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        if ((damageAmount > 0) && (info.owner == owner))
        {
            flash();
    
            addToBot(new BcApplyPowerAction(new UnnaturalHealPower(owner, damageAmount)));
        }
    }
}
