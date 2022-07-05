package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Brimstone extends AbstractRelic
{
    public static final String ID = "Brimstone";
    private static final int StrengthPerTurn = 1;
    
    public Brimstone()
    {
        super("Brimstone", "brimstone.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }
    
    public String getUpdatedDescription()
    {
        return "Start of turn: NL Gain #b" + StrengthPerTurn + " #yStrength and Exhaust a random card that costs 1 or less.";
    }
    
    public void atTurnStartPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        flash();
        addToBot(new RelicAboveCreatureAction(player, this));
        addToBot(new ExhaustRandomCardAction(1));
        addToBot(new TrueWaitAction(.1f));
        addToTop(new BcApplyPowerAction(new StrengthPower(player, StrengthPerTurn)));
    }
    
    public AbstractRelic makeCopy()
    {
        return new Brimstone();
    }
}
