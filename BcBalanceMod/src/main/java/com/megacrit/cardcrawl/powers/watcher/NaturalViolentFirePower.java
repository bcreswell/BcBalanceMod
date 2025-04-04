package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.stances.*;

public class NaturalViolentFirePower extends BcPowerBase
{
    public static final String POWER_ID = "NaturalViolentFirePower";
    
    public NaturalViolentFirePower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Natural, Violent Fire";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "naturalViolentFire32x32.png";
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
        return "Start Turn in Wrath: NL Gain #b" + amount + " energy and draw #b"+BcUtility.getCardCountString(amount)+". NL NL End Turn in Wrath: NL Retain your Block.";
    }
    //endregion

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        //implemented this directly in GameActionManager.java instead.
//        AbstractPower blurPower = player.getPower(BlurPower.POWER_ID);
//        //Dont apply blur if you already have it
//        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) &&
//            (blurPower == null))
//        {
//            addToBot(new BcApplyPowerAction(new BlurPower(player, 1)));
//        }
    }

    public void atStartOfTurnPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        AbstractPower simmeringFuryPower = player.getPower(WrathNextTurnPower.POWER_ID);
        
        //special case lets this still work with simmering fury
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) || (simmeringFuryPower != null))
        {
            flash();
            addToBot(new GainEnergyAction(amount));
            addToBot(new DrawCardAction(amount));
        }
    }
}
