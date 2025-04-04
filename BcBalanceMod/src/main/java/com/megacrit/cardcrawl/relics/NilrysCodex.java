package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.BcUtility;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryImprovedAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class NilrysCodex extends CustomRelic implements ClickableRelic
{
    public static final String ID = "Nilry's Codex";
    public static final int UsesPerCombat = 3;
    public static final int Choices = 3;
    boolean isEnabled;
    
    public NilrysCodex()
    {
        super(ID, "codex.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return "Right Click: Choose 1 of 3 cards to Create. NL NL Can be used once per turn, and up to #b" + UsesPerCombat + " times per combat.";
    }
    
    public void atBattleStart()
    {
        setCounter(UsesPerCombat);
    }
    
    public void atTurnStartPostDraw()
    {
        if (counter > 0)
        {
            isEnabled = true;
            grayscale = false;
            flash(); //flash to remind player this is available
        }
    }
    
    public void onPlayerEndTurn()
    {
        isEnabled = false;
    }
    
    public void onVictory()
    {
        //setting it to zero to clarify that charges don't persist between combats.
        setCounter(0);
        grayscale = false;
        usedUp = false;
    }
    
    @Override
    public void onRightClick()
    {
        if (BcUtility.isPlayerInCombat() &&
            isEnabled &&
            (counter > 0))
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DiscoveryImprovedAction(Choices, false, false, null, null, true));
            setCounter(counter - 1);
            flash();
            grayscale = true;
            isEnabled = false;
        }
    }
    
    @Override
    public void setCounter(int counter)
    {
        this.counter = counter;
        if (counter <= 0)
        {
            this.counter = -2;
            grayscale = true;
            usedUp = true;
        }
        else
        {
            grayscale = false;
            usedUp = false;
        }
    }
}
