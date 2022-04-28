package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class MutagenicStrength extends AbstractRelic
{
    public static final String ID = "MutagenicStrength";
    private static final int STR_AMT = 3;
    
    public MutagenicStrength()
    {
        super("MutagenicStrength", "mutagen.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }
    
    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + 3 + this.DESCRIPTIONS[2];
    }
    
    public void atBattleStart()
    {
        this.flash();
        
        addToBot(new BcApplyPowerAction(new StrengthPower(AbstractDungeon.player, 3)));
        addToBot(new BcApplyPowerAction(new LoseStrengthPower(AbstractDungeon.player, 3)));
        
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    public AbstractRelic makeCopy()
    {
        return new MutagenicStrength();
    }
}
