package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class IntangiblePlayerPower extends BcPowerBase
{
    public static final String POWER_ID = "IntangiblePlayer";
    
    public IntangiblePlayerPower(AbstractCreature owner, int turns)
    {
        super(owner, turns);
        priority = 75;
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Intangible";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "intangible";
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
        return "Reduce ALL damage taken and HP loss to #b1. NL Your block becomes ghostly while Intangible.";
    }
    //endregion
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_INTANGIBLE", 0.05F);
    }
    
    public void onInitialApplication()
    {
        addToTop(new GhostifyBlockAction(owner));
    }
    
    public void onGainedBlock(float blockAmount)
    {
        addToTop(new GhostifyBlockAction(owner));
    }
    
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type)
    {
        if (damage > 1.0F)
        {
            damage = 1.0F;
        }
        
        return damage;
    }
    
    @Override
    public void onRemove()
    {
        //this will return the block to normal if no longer intangible
        addToBot(new GhostifyBlockAction(owner));
    }
    
    public void atEndOfRound()
    {
        flash();
        
        if (amount == 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
        else
        {
            addToBot(new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
    }
}
