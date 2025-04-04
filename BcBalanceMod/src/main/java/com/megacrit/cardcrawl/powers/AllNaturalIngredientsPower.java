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
        return "When you Inflict #yWeak on an enemy, also Inflict #b" + amount + " #yPoison.";
    }
    //endregion
    
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((target != AbstractDungeon.player) &&
            (power.ID.equals(WeakPower.POWER_ID)))
        {
            flash();
            //dont apply it multiple times because that's way too strong with snecko skull and Sadistic Nature
//            for (int i = 0; i < weak.amount; i++)
//            {
//                addToBot(new BcApplyPowerAction(target, source, new PoisonPower(target, source, amount), AbstractGameAction.AttackEffect.POISON));
//            }
            
            addToBot(new BcApplyPowerAction(target, source, new PoisonPower(target, source, amount), AbstractGameAction.AttackEffect.POISON, false));
        }
    }
}
