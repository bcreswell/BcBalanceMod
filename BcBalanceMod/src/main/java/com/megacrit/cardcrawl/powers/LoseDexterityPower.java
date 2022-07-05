package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LoseDexterityPower extends BcPowerBase
{
    public static final String POWER_ID = "DexLoss";
    private static final PowerStrings powerStrings;
    
    public LoseDexterityPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return powerStrings.NAME;
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "dexterityDown32x32.png";
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
    public boolean isAppliedQuietly()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
    //endregion
    
    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        addToBot(new BcApplyPowerAction(new DexterityPower(owner, -amount)));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "DexLoss"));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DexLoss");
    }
}
