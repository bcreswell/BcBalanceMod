package com.megacrit.cardcrawl.relics;

import basemod.abstracts.*;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.*;

public class BcHoveringKite extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcHoveringKite");
    private boolean triggeredThisTurn = false;
    
    public BcHoveringKite()
    {
        super(ID, "kite.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
    
    public void atTurnStart()
    {
        grayscale = false;
        triggeredThisTurn = false;
    }
    
    public void onManualDiscard()
    {
        if (!triggeredThisTurn)
        {
            grayscale = true;
            triggeredThisTurn = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }
    
    @Override
    public void onVictory()
    {
        grayscale = false;
    }
}
