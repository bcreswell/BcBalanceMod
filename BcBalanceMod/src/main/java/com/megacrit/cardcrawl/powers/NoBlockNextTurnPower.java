package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import bcBalanceMod.baseCards.BcPowerBase;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class NoBlockNextTurnPower extends BcPowerBase
{
    public static final String POWER_ID = "NoBlockNextTurnPower";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "No Block Next Turn";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "noBlock";
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Unique;
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
        return "Next turn, you won't be able to gain Block from cards.";
    }
    //endregion
    
    public NoBlockNextTurnPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }

    public void atEndOfRound()
    {
        addToBot(new BcApplyPowerAction(new NoBlockPower(player, 1, false)));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }
}
