//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnforeseenAllyPower extends BcPowerBase
{
    public static final String POWER_ID = "UnforeseenAllyPower";
    
    public UnforeseenAllyPower(AbstractCreature owner, int amt)
    {
        super(owner, amt);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Unforeseen Ally";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "stasis";
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
            return "Start of turn: NL Create a random upgraded foreign Skill. It will become Ethereal.";
        }
        else
        {
            return "Start of turn: NL Create #b" + amount + " random upgraded foreign Skills. They will become Ethereal.";
        }
    }
    //endregion
    
    //not using atStartOfTurnPostDraw() here because i don't want it shoving ethereal cards into your discard pile when you're full.
    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            
            for (int i = 0; i < amount; i++)
            {
                addToBot(new UnforseenAllyAction());
            }
        }
    }
}
