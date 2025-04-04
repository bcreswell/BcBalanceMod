
package com.megacrit.cardcrawl.orbs;

import bcBalanceMod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DecryptionDancePower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect.OrbFlareColor;

public class Frost extends AbstractOrb
{
    public static final String ORB_ID = "Frost";
    private static final OrbStrings orbString;
    private boolean hFlip1 = MathUtils.randomBoolean();
    private boolean hFlip2 = MathUtils.randomBoolean();
    private float vfxTimer = 1.0F;
    private float vfxIntervalMin = 0.15F;
    private float vfxIntervalMax = 0.8F;
    
    public Frost()
    {
        this.ID = "Frost";
        this.name = orbString.NAME;
        this.baseEvokeAmount = 7;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5F;
        evokeColor = new Color(.7F, .7F, 1F, c.a);
    }
    
    public void updateDescription()
    {
        this.applyFocus();
        
        //it's better to explain how the values are calculated in the tooltip. The final values are clearly visualized on the orb itself.
        description = "#yEvoke: NL Gain ( #b" + baseEvokeAmount + " + #yFocus ) #yBlock. NL ( minimum Evoke: #b" + baseEvokeAmount + " #yBlock ) NL NL #yEnd #yof #yTurn: NL Gain ( #b" + basePassiveAmount + " + #yFocus ) #yBlock.";
    }
    
    public void onEvoke()
    {
        if (evokeAmount > 0)
        {
            int evokeTimes = 1;
            
            for(int i =0; i < evokeTimes; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, evokeAmount));
            }
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
    
    public void updateAnimation()
    {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean())
            {
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            }
            
            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
        
    }
    
    public void onEndOfTurn()
    {
        if (this.passiveAmount > 0)
        {
            float speedTime = 0.6F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE)
            {
                speedTime = 0.0F;
            }
            
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareColor.FROST), speedTime));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.passiveAmount, true));
        }
    }
    
    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
    }
    
    public void render(SpriteBatch sb)
    {
        sb.setColor(this.c);
        sb.draw(ImageMaster.FROST_ORB_RIGHT, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_LEFT, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F - this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(ImageMaster.FROST_ORB_MIDDLE, this.cX - 48.0F - this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 2.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, this.hFlip2, false);
        this.renderText(sb);
        this.hb.render(sb);
    }
        
    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
    }
    
    public AbstractOrb makeCopy()
    {
        return new Frost();
    }
    
    static
    {
        orbString = CardCrawlGame.languagePack.getOrbString("Frost");
    }
}
