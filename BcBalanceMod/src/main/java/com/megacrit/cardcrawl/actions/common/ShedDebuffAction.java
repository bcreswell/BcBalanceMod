package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class ShedDebuffAction extends AbstractGameAction {
    
    public ShedDebuffAction(AbstractCreature target, AbstractCreature source) {
        setValues(target, source, 1);
        
        if (Settings.FAST_MODE) {
            duration = startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            duration = startDuration = Settings.ACTION_DUR_FAST;
        }
        
        actionType = AbstractGameAction.ActionType.REDUCE_POWER;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            AbstractPower vuln = target.getPower(VulnerablePower.POWER_ID);
            AbstractPower weak = target.getPower(WeakPower.POWER_ID);
            AbstractPower frail = target.getPower(FrailPower.POWER_ID);
            
            ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
            if (vuln != null)
            {
                powersToRemove.add(vuln);
            }
            
            if (weak != null)
            {
                powersToRemove.add(weak);
            }
            
            if (frail != null)
            {
                powersToRemove.add(frail);
            }
            
            if (!powersToRemove.isEmpty())
            {
                AbstractPower reduceMe = powersToRemove.get(AbstractDungeon.miscRng.random(0, powersToRemove.size()-1));
                
                if (reduceMe.amount > 1)
                {
                    reduceMe.reducePower(amount);
                    reduceMe.updateDescription();
                    AbstractDungeon.onModifyPower();
                }
                else
                {
                    addToTop(new RemoveSpecificPowerAction(target, source, reduceMe));
                }
            }
        }
        
        tickDuration();
    }
}
