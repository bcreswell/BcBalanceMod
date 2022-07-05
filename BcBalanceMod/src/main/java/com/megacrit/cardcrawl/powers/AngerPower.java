//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class AngerPower extends AbstractPower
{
    public static final String POWER_ID = "Anger";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int EnrageThreshold = 5;
    
    public AngerPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Anger";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("anger");
    }
    
    public void updateDescription()
    {
        description = "When your opponent gains " + EnrageThreshold + " or more Block, Gain #b" + amount + " Strength.";
    }
    
    public int onPlayerGainedBlock(float blockAmount)
    {
        if (blockAmount >= EnrageThreshold)
        {
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            flash();
        }

        return MathUtils.floor(blockAmount);
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Anger");
        
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
