package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;

public class HellfirePower extends BcPowerBase
{
    public static final String POWER_ID = "HellfirePower";
    
    public HellfirePower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Hellfire";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "hellfire32x32.png";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean isAppliedQuietly()
    {
        return true;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal #b" + amount + " damage to ALL enemies at the start of your next turn.";
    }
    //endregion
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            if (amount > 0)
            {
                flash();
                addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                
                amount = 0;
                addToBot(new RemovePowerIfEmptyAction(owner, POWER_ID, true));
            }
        }
    }
}
