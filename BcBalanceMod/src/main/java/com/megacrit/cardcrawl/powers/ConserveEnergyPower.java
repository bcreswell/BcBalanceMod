package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class ConserveEnergyPower extends BcPowerBase
{
    public static final String POWER_ID = "ConserveEnergyPower";
    
    public ConserveEnergyPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Conserve Energy";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "conserve";
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.Unique;
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
        if (amount <= 1)
        {
            return "Any left over #yEnergy will be Retained for next turn.";
        }
        else
        {
            return "Any left over #yEnergy will be Retained for next turn. #b" + amount + " turns remaining.";
        }
    }
    //endregion
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            int retainEnergyAmt = EnergyPanel.totalCount;
            if (retainEnergyAmt > 0)
            {
                //this shouldn't generate extra energy in the case that you're already conserving it.
                if ((player.getRelic(IceCream.ID) == null))
                {
                    flash();
                    EnergyPanel.totalCount -= retainEnergyAmt;
                    addToBot(new BcApplyPowerAction(new EnergizedPower(player, retainEnergyAmt)));
                }
                
                addToBot(new ReducePowerAction(owner, owner, POWER_ID, 1));
            }
        }
    }
}
