package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.stance.*;

public class DemonFormPower extends AbstractPower
{
    public static final String POWER_ID = "Demon Form";
    private static final PowerStrings powerStrings;
    protected float particleTimer;
    
    public DemonFormPower(AbstractCreature owner, int strengthAmount)
    {
        this.name = powerStrings.NAME;
        this.ID = "Demon Form";
        this.owner = owner;
        this.amount = strengthAmount;
        this.updateDescription();
        this.loadRegion("demonForm");
    }
    
    public void updateDescription()
    {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    
    public void updateParticles()
    {
        if (!Settings.DISABLE_EFFECTS && (amount > 0))
        {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F)
            {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new DemonFormParticleEffect());
            }
        }
    }
    
    public void atStartOfTurnPostDraw()
    {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Demon Form");
    }
}
