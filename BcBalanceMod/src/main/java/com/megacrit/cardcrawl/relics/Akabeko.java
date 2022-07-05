package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class Akabeko extends AbstractRelic
{
    public static final String ID = "Akabeko";
    private static final int VIGOR = 8;
    
    public Akabeko()
    {
        super("Akabeko", "akabeko.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
    }
    
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + 8 + DESCRIPTIONS[1];
    }
    
    public void atBattleStart()
    {
        flash();
        grayscale = false;
        addToTop(new BcApplyPowerAction(new VigorPower(AbstractDungeon.player, 8)));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    public void onRefreshHand()
    {
        if (BcUtility.isPlayerInCombat() &&
                    BcUtility.getPowerAmount(VigorPower.POWER_ID) == 0)
        {
            grayscale = true;
        }
    }
    
    public void onVictory()
    {
        grayscale = false;
    }
    
    public AbstractRelic makeCopy()
    {
        return new Akabeko();
    }
}
