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
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;

public class WaxOnPower extends BcPowerBase
{
    public static final String POWER_ID = "WaxOnPower";
    
    public WaxOnPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Wax On";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "waxOn32x32.png";
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
            return "Each turn: NL The first time you Discard, NL Draw a card.";
        }
        else
        {
            return "Each turn: NL The first " + amount + " times you Discard, NL Draw a card.";
        }
    }
    //endregion
    
    public void atStartOfTurnPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        AbstractPower waxOffPower = player.getPower(WaxOffPower.POWER_ID);
        if (waxOffPower != null)
        {
            waxOffPower.amount = amount;
        }
        else
        {
            addToBot(new BcApplyPowerAction(new WaxOffPower(player, amount)));
        }
    }
}
