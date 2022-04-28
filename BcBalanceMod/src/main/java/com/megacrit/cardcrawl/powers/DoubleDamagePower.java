package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.stance.PhantasmalKillerParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

public class DoubleDamagePower extends AbstractPower
{
    public static final String POWER_ID = "Double Damage";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justApplied = false;
    protected float particleTimer;
    protected float particleTimer2;
    
    public DoubleDamagePower(AbstractCreature owner, int amount, boolean isSourceMonster)
    {
        this.name = NAME;
        this.ID = "Double Damage";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("doubleDamage");
        this.justApplied = isSourceMonster;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 6;
    }
    
    public void updateParticles()
    {
        if (!Settings.DISABLE_EFFECTS)
        {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F)
            {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new PhantasmalKillerParticleEffect());
            }
        }
    }
    
    public void atEndOfRound()
    {
        if (this.justApplied)
        {
            this.justApplied = false;
        }
        else
        {
            if (this.amount == 0)
            {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Double Damage"));
            }
            else
            {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, "Double Damage", 1));
            }
            
        }
    }
    
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        
    }
    
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Damage");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
