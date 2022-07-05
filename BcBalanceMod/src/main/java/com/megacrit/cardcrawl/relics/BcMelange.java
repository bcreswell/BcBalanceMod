package com.megacrit.cardcrawl.relics;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BcMelange extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcMelange");
    public static final int ScryAmount = 3;
    boolean skipScryFirstTurn = true;
    
    public BcMelange()
    {
        super(ID, "melange.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }
    
    @Override
    public String getUpdatedDescription()
    {
        return "At the start of your turn, NL Scry #b" + ScryAmount + ".";
    }
    
    @Override
    public void atBattleStartPreDraw()
    {
        scry();
        skipScryFirstTurn = true;
    }
    
    public void atTurnStart()
    {
        if (skipScryFirstTurn)
        {
            //using atBattleStartPreDraw() for the first turn because atTurnStart() doesn't happen until you've already drawn your first 5 cards.
            skipScryFirstTurn = false;
        }
        else
        {
            scry();
        }
    }
    
    void scry()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (player != null)
        {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            
            addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
            addToBot(new ScryAction(ScryAmount));
        }
    }
}
