package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Melange extends AbstractRelic
{
    public static final String ID = "Melange";
    public static final int ScryAmount = 3;
    
    public Melange()
    {
        super("Melange", "melange.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + ScryAmount + this.DESCRIPTIONS[1];
    }
    
    public void onShuffle()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    
        int actualScryAmount = BcUtility.getActualScryAmount(ScryAmount);
        addToBot(new ScryAction(actualScryAmount));
    }
    
    public AbstractRelic makeCopy()
    {
        return new Melange();
    }
}
