//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.orbs;

import bcBalanceMod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor;

import java.util.*;

public class Lightning extends AbstractOrb
{
    public static final String ORB_ID = "Lightning";
    private static final OrbStrings orbString;
    private float vfxTimer = 1.0F;
    private static final float PI_DIV_16 = 0.19634955F;
    private static final float ORB_WAVY_DIST = 0.05F;
    private static final float PI_4 = 12.566371F;
    private static final float ORB_BORDER_SCALE = 1.2F;
    public static final float LockOnMultiplier = 1.5f;
    
    public Lightning()
    {
        this.ID = "Lightning";
        this.img = ImageMaster.ORB_LIGHTNING;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 8;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 3;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
        evokeColor = new Color(1F, 1F, .6F, c.a);
    }
    
    public void updateDescription()
    {
        this.applyFocus();
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            this.description = "#yPassive: At the end of turn, deal #b" + this.passiveAmount + " damage to a random enemy. NL #yEvoke: Deal #b" + this.evokeAmount + " damage to a random enemy. (minimum: " + this.baseEvokeAmount + ").";
        }
        else
        {
            this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + this.evokeAmount + orbString.DESCRIPTION[2];
        }
    }
    
    public void onEvoke()
    {
        if (AbstractDungeon.player.hasPower("Electro"))
        {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), true));
        }
        else
        {
            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), false));
        }
    }
    
    //evoke no longer goes below the base value
    public void applyFocus()
    {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power != null && !this.ID.equals("Plasma"))
        {
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
            this.evokeAmount = Math.max(this.baseEvokeAmount, this.baseEvokeAmount + power.amount);
        }
        else
        {
            this.passiveAmount = this.basePassiveAmount;
            this.evokeAmount = this.baseEvokeAmount;
        }
    }
    
    public void onEndOfTurn()
    {
        if (AbstractDungeon.player.hasPower("Electro"))
        {
            float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE)
            {
                speedTime = 0.0F;
            }
            
            if (this.passiveAmount > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.LIGHTNING), speedTime));
                AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageType.THORNS), true));
            }
        }
        else
        {
            if (this.passiveAmount > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageType.THORNS), this, false));
            }
        }
    }
    
    private void triggerPassiveEffect(DamageInfo info, boolean hitAll)
    {
        if (!hitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null)
            {
                float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
                if (Settings.FAST_MODE)
                {
                    speedTime = 0.0F;
                }
                
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.LIGHTNING), speedTime));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        }
        else
        {
            float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE)
            {
                speedTime = 0.0F;
            }
            
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.LIGHTNING), speedTime));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(info.base, true), DamageType.THORNS, AttackEffect.NONE));
            Iterator var7 = AbstractDungeon.getMonsters().monsters.iterator();
            
            while (var7.hasNext())
            {
                AbstractMonster m3 = (AbstractMonster) var7.next();
                if (!m3.isDeadOrEscaped() && !m3.halfDead)
                {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), speedTime));
                }
            }
            
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
        }
        
    }
    
    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }
    
    public void updateAnimation()
    {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean())
            {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
            }
            
            this.vfxTimer = MathUtils.random(0.15F, 0.8F);
        }
        
    }
    
    public void render(SpriteBatch sb)
    {
        this.shineColor.a = this.c.a / 2.0F;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
    }
    
    public AbstractOrb makeCopy()
    {
        return new Lightning();
    }
    
    public static int applyLockOn(AbstractCreature target, int baseDamage)
    {
        if (target.hasPower(LockOnPower.POWER_ID))
        {
            return (int) ((float) baseDamage * LockOnMultiplier);
        }
        
        return baseDamage;
    }
    
    public static int[] createDamageMatrix(int baseDamage)
    {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        int[] retVal = new int[monsters.size()];
        
        for (int i = 0; i < retVal.length; ++i)
        {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);
            info.output = applyLockOn(monsters.get(i), baseDamage);
            
            retVal[i] = info.output;
        }
        
        return retVal;
    }
    
    static
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Lightning");
    }
}
