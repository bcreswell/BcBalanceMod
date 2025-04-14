package com.megacrit.cardcrawl.stances;

import bcBalanceMod.BcUtility;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.DivinityParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

public class DivinityStance extends AbstractStance
{
    public static final String STANCE_ID = "Divinity";
    private static final StanceStrings stanceString;
    private static long sfxId;
    
    public DivinityStance()
    {
        this.ID = "Divinity";
        this.name = stanceString.NAME;
        this.updateDescription();
    }
    
    public void updateAnimation()
    {
        if (!Settings.DISABLE_EFFECTS)
        {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F)
            {
                this.particleTimer = 0.2F;
                AbstractDungeon.effectsQueue.add(new DivinityParticleEffect());
            }
        }
        
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F)
        {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Divinity"));
        }
    }
    
    public void atStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
    }
    
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        return type == DamageInfo.DamageType.NORMAL ? damage * 3.0F : damage;
    }
    
    public void updateDescription()
    {
        description = "Upon triggering this #yStance, gain [W] [W] [W] . Attacks deal triple damage. Retain your energy until next turn. Exit this #yStance at the start of your next turn.";
        //description = "Upon triggering this #yStance, gain [W] [W] [W] . Attacks deal triple damage. Exit this #yStance at the start of your next turn.";
    }
    
    //happens even if you're already in the stance
    public void onRenteringStance()
    {
        //AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
    }
    
    public void onEnterStance()
    {
        if (sfxId != -1L)
        {
            this.stopIdleSfx();
        }
        
        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Divinity"));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
        
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new BcApplyPowerAction(new ConserveEnergyPower(player, 1)));
        
        AbstractPower mantra = BcUtility.getPower(MantraPower.POWER_ID);
        if (mantra != null)
        {
            ((MantraPower)mantra).convertToHealingInDivinity();
        }
    }
    
    public void onExitStance()
    {
        this.stopIdleSfx();
    }
    
    public void stopIdleSfx()
    {
        if (sfxId != -1L)
        {
            CardCrawlGame.sound.stop("STANCE_LOOP_DIVINITY", sfxId);
            sfxId = -1L;
        }
    }
    
    static
    {
        stanceString = CardCrawlGame.languagePack.getStanceString("Divinity");
        sfxId = -1L;
    }
}
