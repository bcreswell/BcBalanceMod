package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;

public class FlameBarrierEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private static final float X_RADIUS;
    private static final float Y_RADIUS;
    private boolean flashedBorder = true;
    private Vector2 v = new Vector2(0.0F, 0.0F);
    boolean reverse;
    
    public FlameBarrierEffect(float x, float y)
    {
        duration = 0.5F;
        this.x = x;
        this.y = y;
        renderBehind = false;
        reverse = false;
    }
    
    public FlameBarrierEffect(float x, float y, boolean reverse)
    {
        duration = 0.5F;
        this.x = x;
        this.y = y;
        renderBehind = false;
        this.reverse = reverse;
    }
    
    public void update()
    {
        if (flashedBorder)
        {
            CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
            flashedBorder = false;
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(new Color(1.0F, 1.0F, 0.1F, 1.0F)));
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.4F, 0.1F, 1.0F)));
        }
        
        float tmp = Interpolation.fade.apply(-209.0F, 30.0F, duration / 0.5F) * 0.017453292F;
        v.x = MathUtils.cos(tmp) * X_RADIUS;
        if (reverse)
        {
            v.x *= -1;
        }
        v.y = -MathUtils.sin(tmp) * Y_RADIUS;
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, y + v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, y + v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(x + v.x + MathUtils.random(-30.0F, 30.0F) * Settings.scale, y + v.y + MathUtils.random(-10.0F, 10.0F) * Settings.scale));
        AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(x + v.x, y + v.y));
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F)
        {
            isDone = true;
        }
        
    }
    
    public void render(SpriteBatch sb)
    {
    }
    
    public void dispose()
    {
    }
    
    static
    {
        X_RADIUS = 200.0F * Settings.scale;
        Y_RADIUS = 250.0F * Settings.scale;
    }
}
