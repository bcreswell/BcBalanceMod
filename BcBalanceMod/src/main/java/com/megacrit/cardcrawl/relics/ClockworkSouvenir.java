package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class ClockworkSouvenir extends AbstractRelic
{
    public static final String ID = "ClockworkSouvenir";
    
    public ClockworkSouvenir()
    {
        super("ClockworkSouvenir", "clockwork.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription()
    {
        return "Start each combat with #b1 #yArtifact. NL NL (" + ArtifactPower.Footnote + ")";
    }
    
    public void atBattleStart()
    {
        flash();
        addToTop(new BcApplyPowerAction(new ArtifactPower(AbstractDungeon.player, 1)));
    }
    
    public AbstractRelic makeCopy()
    {
        return new ClockworkSouvenir();
    }
}
