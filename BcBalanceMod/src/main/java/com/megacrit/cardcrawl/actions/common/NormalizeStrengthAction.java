package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.*;
import com.evacipated.cardcrawl.mod.stslib.blockmods.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class NormalizeStrengthAction extends AbstractGameAction
{
    AbstractCreature owner;
    
    public NormalizeStrengthAction(AbstractCreature owner)
    {
        this.owner = owner;
    }
    
    public void update()
    {
        //processes strength rebound first
        //then, if still negative, set strength to zero
        //this means you can't use temporary strength down to permanently remove their strength
        
        int reboundStrength = BcUtility.getPowerAmount(GainStrengthPower.POWER_ID, owner);
        int currentStrength = BcUtility.getPowerAmount(StrengthPower.POWER_ID, owner);
        int afterReboundStr = currentStrength + reboundStrength;
        
        if (afterReboundStr < 0)
        {
            //make sure the rebound is guaranteed to get us all the way back to zero
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -afterReboundStr), -afterReboundStr));
        }
        
        addToBot(new RemoveSpecificPowerAction(owner, owner, WeakPower.POWER_ID));
        
        isDone = true;
    }
}
