package com.megacrit.cardcrawl.powers;

import basemod.devcommands.power.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BcCombustPower extends BcPowerBase
{
    public static final String POWER_ID = "BcCombustPower";
    
    public BcCombustPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Combust";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "combust";
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
        return "When you lose HP, deal that much damage to ALL enemies at the start of your next turn.";
    }
    //endregion
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            //look at the various hellfire stacks and pick the biggest one
            AbstractPower biggestHellfire = null;
            for (AbstractPower power : player.powers)
            {
                if (power.ID.startsWith(HellfirePower.POWER_ID))
                {
                    if ((biggestHellfire == null) || (power.amount > biggestHellfire.amount))
                    {
                        biggestHellfire = power;
                    }
                }
            }
            
            if ((biggestHellfire != null) && (biggestHellfire.amount >= Combust.CombustThreshold))
            {
                biggestHellfire.flash();
                addToBot(new RemoveSpecificPowerAction(owner, owner, biggestHellfire.ID)); //not POWER_ID, we need the idOffset instance instead
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(biggestHellfire.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
    
    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        if (damageAmount > 0)
        {
            flash();
            for (int i = 0; i < amount; i++)
            {
                addToBot(new BcApplyPowerAction(new HellfirePower(Integer.toString(i), damageAmount)));
            }
        }
    }
}
