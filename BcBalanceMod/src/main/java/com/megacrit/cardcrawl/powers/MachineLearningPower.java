package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.MachineLearning;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.HashMap;

public class MachineLearningPower extends BcPowerBase
{
    public static final String POWER_ID = "Machine Learning";
    
    HashMap<AbstractCreature, Integer> damagePerMonster = new HashMap<AbstractCreature, Integer>();
    
    public MachineLearningPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Machine Learning";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "evolve";
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
        return "When you take "+ MachineLearning.DamageThreshold+" or more total damage from an enemy's attack, gain #b"+amount+" Dexterity.";
    }
    //endregion
    
    @Override
    public void atStartOfTurn()
    {
        //reset the damage count each turn
        damagePerMonster.clear();
    }
    
    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        //this should add up the damage from each monster each turn and
        // only apply the dex buff on the first instance of damage from each monster
        // that pushes their damage total >= DamageThreshold.
        
        //why so complicated? Didn't want multi attacks to apply dex bonus multiple times.
        
        if ((info.type == DamageInfo.DamageType.NORMAL) &&
            (info.owner != null) &&
            (info.owner != owner) &&
            (damageAmount > 0))
        {
            Integer previousTotal = 0;
            if (damagePerMonster.containsKey(info.owner))
            {
                previousTotal = damagePerMonster.get(info.owner);
            }
            
            Integer newTotal = previousTotal + damageAmount;
            
            damagePerMonster.put(info.owner, newTotal);
            
            if ((previousTotal < MachineLearning.DamageThreshold) &&
                (newTotal >= MachineLearning.DamageThreshold))
            {
                flash();
                addToBot(new BcApplyPowerAction(new DexterityPower(player, amount)));
            }
        }
        
        return damageAmount;
    }
}
