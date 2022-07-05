package com.megacrit.cardcrawl.relics;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NilrysCodex extends CustomRelic implements ClickableRelic
{
    public static final String ID = "Nilry's Codex";
    public static final int CardsPerCombat = 3;
    public static final int CardsChoices = 4;
    boolean isEnabled;
    
    public NilrysCodex()
    {
        super("Nilry's Codex", "codex.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return "Up to #b" + CardsPerCombat + " times per combat: NL Right click to create 1 of " + CardsChoices + " random cards.";
    }
    
    public void onVictory()
    {
        //just to clarify that charges don't persist between combats.
        setCounter(0);
        grayscale = false;
        usedUp = false;
    }
    
    public void atTurnStartPostDraw()
    {
        isEnabled = true;
    }
    
    public void onPlayerEndTurn()
    {
        isEnabled = false;
    }
    
    public AbstractRelic makeCopy()
    {
        return new NilrysCodex();
    }
    
    public void atBattleStart()
    {
        setCounter(CardsPerCombat);
    }
    
    @Override
    public void onRightClick()
    {
        if (BcUtility.isPlayerInCombat() &&
                    isEnabled &&
                    (counter > 0))
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DiscoveryImprovedAction(CardsChoices, false, false, null, null));
            setCounter(counter - 1);
            flash();
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
