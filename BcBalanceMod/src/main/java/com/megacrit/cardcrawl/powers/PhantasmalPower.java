package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

public class PhantasmalPower extends AbstractPower
{
    public static final String POWER_ID = "Phantasmal";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public PhantasmalPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = "Phantasmal";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("phantasmal");
    }
    
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            this.description = DESCRIPTIONS[0];
        }
        else
        {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
    
    public void atStartOfTurn()
    {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DoubleDamagePower(this.owner, 1, false), this.amount));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, "Phantasmal", 1));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Phantasmal");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
