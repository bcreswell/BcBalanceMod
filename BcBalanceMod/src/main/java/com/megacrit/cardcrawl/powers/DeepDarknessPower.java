//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

public class DeepDarknessPower extends BcPowerBase
{
    public static final String POWER_ID = "DeepDarknessPower";
    
    public DeepDarknessPower(AbstractCreature owner, int amount, boolean upgraded)
    {
        super(owner, amount);
        this.upgraded = upgraded;
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "The Deep Darkness";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "darkembrace";
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
        if (amount == 1)
        {
            return "End of turn: NL Trigger the passive on all #yDark orbs. NL If you have an empty orb slot, NL Channel a #yDark orb.";
        }
        else
        {
            return "End of turn: NL Trigger the passive on all #yDark orbs #b" + amount + " times. NL If you have an empty orb slot, NL Channel a #yDark orb.";
        }
    }
    //endregion
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            for (int i = 0; i < amount; i++)
            {
                addToBot(new TrueWaitAction(.1f));
                addToBot(new BcDarkImpulseAction());
            }
            
            if (AbstractDungeon.player.hasEmptyOrb())
            {
                addToBot(new TrueWaitAction(.1f));
                addToBot(new ChannelAction(new Dark()));
            }
        }
    }
}
