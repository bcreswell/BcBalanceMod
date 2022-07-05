package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.*;

public class UnbreakableHeartPower extends BcPowerBase
{
    public static final String POWER_ID = "UnbreakableHeartPower";
    
    public UnbreakableHeartPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
        
        priority = 99;
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Unbreakable Heart";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "heartDef";
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
    public boolean isAppliedQuietly()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (amount <= 0)
        {
            return "Invulnerable until the start of next turn.";
        }
        else
        {
            return "Maximum possible HP loss this turn: #b" + amount + ".";
        }
    }
    //endregion
    
    @Override
    public void stackPower(int stackAmount)
    {
        if (amount + stackAmount > UnbreakableHeart.DamageCap)
        {
            stackAmount = UnbreakableHeart.DamageCap - amount;
        }
        else if (amount + stackAmount < 0)
        {
            stackAmount = 0;
            amount = 0;
        }
        
        super.stackPower(stackAmount);
    }
    
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        if (damageAmount > amount)
        {
            damageAmount = amount;
            flash();
        }
        
        addToTop(new BcApplyPowerAction(new UnbreakableHeartPower(owner, -damageAmount)));
        
        return damageAmount;
    }
    
    public void atStartOfTurn()
    {
        if (amount != UnbreakableHeart.DamageCap)
        {
            //brief wait so players can see the amount
            addToBot(new TrueWaitAction(.5f));
            addToBot(new BcApplyPowerAction(new UnbreakableHeartPower(owner, UnbreakableHeart.DamageCap)));
        }
    }
}
