package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class NaturalViolentFirePower extends AbstractPower
{
    public static final String POWER_ID = "NaturalViolentFirePower";
    
    public NaturalViolentFirePower(AbstractCreature owner, int amount)
    {
        this.name = "Natural, Violent Fire";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("flameBarrier");
    }
    
    public void updateDescription()
    {
        this.description = "If you're in Wrath at the start of the turn, NL Gain " + amount + " energy.";
    }
    
    public void atStartOfTurnPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        AbstractPower simmeringFuryPower = player.getPower(WrathNextTurnPower.POWER_ID);
        
        //special case lets this still work with simmering fury
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) || (simmeringFuryPower != null))
        {
            flash();
            addToBot(new GainEnergyAction(this.amount));
            //addToBot(new DrawCardAction(this.owner, this.amount));
        }
    }
}
