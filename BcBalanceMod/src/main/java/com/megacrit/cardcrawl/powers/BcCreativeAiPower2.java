//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import basemod.devcommands.power.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BcCreativeAiPower2 extends BcPowerBase
{
    public static final String POWER_ID = "Creative AI2";
    
    public BcCreativeAiPower2(AbstractCreature owner, int amt)
    {
        super(owner, amt);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Creative AI";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "ai";
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
            return "Start of turn: NL Create a random Power. It will become Ethereal. NL (Can't create itself or Self Repair.)";
        }
        else
        {
            return "Start of turn: NL Create #b" + amount + " random Powers. They will become Ethereal. NL (Can't create itself or Self Repair.)";
        }
    }
    //endregion
    
    //not using atStartOfTurnPostDraw() here because i don't want it shoving ethereal powers into your discard pile when you're full.
    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            
            for (int i = 0; i < amount; i++)
            {
                addToBot(new BcCreativeAiAction2());
            }
        }
    }
}
