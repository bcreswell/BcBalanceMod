//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheFireWillSpreadPower extends BcPowerBase
{
    public static final String POWER_ID = "TheFireWillSpreadPower";
    public static final int DamageTimes = 3;
    
    public TheFireWillSpreadPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "The Fire Will Spread";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "theFireWillSpread32x32.png";
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
        return "When you Scry, deal #b" + amount + " damage to a random enemy #b3 times.";
    }
    //endregion
    
    
    @Override
    public void onScry()
    {
        if (amount > 0)
        {
            for (int i = 0; i < TheFireWillSpreadPower.DamageTimes; i++)
            {
                addToBot(new TheFireWillSpreadAction(amount));
            }
        }
    }
}