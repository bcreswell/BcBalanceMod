package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

public class AllNaturalIngredientsPower extends BcPowerBase
{
    public static final String POWER_ID = "AllNaturalIngredientsPower";
    
    public AllNaturalIngredientsPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "All Natural Ingredients";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "sporeCloud";
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
        return "For each #yWeak you inflict on an enemy, also inflict #b" + amount + " #yPoison.";
    }
    //endregion
    
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((target != AbstractDungeon.player) && (power.ID.equals(WeakPower.POWER_ID)))
        {
            flash();
            //apply it multiple times for sake of snecko skull
            for (int i = 0; i < power.amount; i++)
            {
                addToBot(new BcApplyPowerAction(target, source, new PoisonPower(target, source, amount), AbstractGameAction.AttackEffect.POISON));
            }
        }
    }
}
