//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DarkOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor;

public class Dark extends AbstractOrb
{
    public static final String ORB_ID = "Dark";
    private static final OrbStrings orbString;
    public static final String[] DESC;
    private static final float ORB_BORDER_SCALE = 1.2F;
    private float vfxTimer = 0.5F;
    private static final float VFX_INTERVAL_TIME = 0.25F;
    public static final float LockOnMultiplier = 1.25f;
    
    public Dark()
    {
        this.ID = "Dark";
        this.img = ImageMaster.ORB_DARK;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 6; //initial mass
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 6;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5F;
        showEvokeInsteadOfPassive = true;
        evokeColor = new Color(1F, .7F, 1F, c.a);
    }
    
    public void updateDescription()
    {
        applyFocus();
        
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            description = "#yMass: " + evokeAmount + " NL #yPassive: At the end of turn, increase this Orb's mass by #b" + passiveAmount + ". (minimum: " + basePassiveAmount + ") NL #yEvoke: Deal this orb's mass as damage to the weakest enemy.";
        }
        else
        {
            description = DESC[0] + passiveAmount + DESC[1] + evokeAmount + DESC[2] + basePassiveAmount + ".";
        }
    }
    
    public void onEvoke()
    {
        AbstractDungeon.actionManager.addToTop(new DarkOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), AttackEffect.DARK));
    }
    
    public void onEndOfTurn()
    {
        float speedTime = 0.6F / (float) AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE)
        {
            speedTime = 0.0F;
        }
        
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.DARK), speedTime));
        this.evokeAmount += this.passiveAmount;
        this.updateDescription();
    }
    
    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }
    
    public void applyFocus()
    {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power != null)
        {
            this.passiveAmount = Math.max(this.basePassiveAmount, this.basePassiveAmount + power.amount);
        }
        else
        {
            this.passiveAmount = this.basePassiveAmount;
        }
    }
    
    public static int applyLockOn(AbstractCreature target, int baseDamage)
    {
        if (target.hasPower(LockOnPower.POWER_ID))
        {
            return (int) ((float) baseDamage * LockOnMultiplier);
        }
        
        return baseDamage;
    }
    
    public void updateAnimation()
    {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25F;
        }
    }
    
    public void render(SpriteBatch sb)
    {
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.shineColor.a = this.c.a / 3.0F;
        sb.setColor(this.shineColor);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale * 1.2F, this.angle / 1.2F, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.5F, this.scale * 1.5F, this.angle / 1.4F, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_DARK_CHANNEL", 0.1F);
    }
    
    public AbstractOrb makeCopy()
    {
        return new Dark();
    }
    
    static
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Dark");
        DESC = orbString.DESCRIPTION;
    }
}
