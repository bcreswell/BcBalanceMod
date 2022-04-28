package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LoseStrengthPower extends BcPowerBase
{
    public static final String POWER_ID = "Flex";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flex");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
    
    public LoseStrengthPower(AbstractCreature owner, int newAmount)
    {
        super(owner,newAmount);
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Strength Down";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "flex";
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
        return DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    //endregion
    
    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        
        addToBot(new BcApplyPowerAction(new StrengthPower(owner, -amount)));
        addToBot(new RemoveSpecificPowerAction(owner, owner, "Flex"));
    }
}
