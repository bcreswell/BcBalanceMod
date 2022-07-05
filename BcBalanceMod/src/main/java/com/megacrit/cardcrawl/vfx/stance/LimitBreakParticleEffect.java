package com.megacrit.cardcrawl.vfx.stance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LimitBreakParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float dur_div2;
    private float delay;
    private float acceleration;
    private TextureAtlas.AtlasRegion img;
    
    public LimitBreakParticleEffect(float delay, float range, boolean isYellow)
    {
        img = ImageMaster.GLOW_SPARK;
        duration = MathUtils.random(1.4F, 2.1F);
        scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        this.delay = delay;
        if (isYellow)
        {
            float c = MathUtils.random(0.5F, 1.0F);
            color = new Color(c, c, MathUtils.random(0F, .5F), 0.0F);
        }
        else
        {
            float c = MathUtils.random(0.5F, 1.0F);
            color = new Color(c, c, c, 0.0F);
        }
        acceleration = MathUtils.random(0.1F, .22F);
        x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - range * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + range * Settings.scale);
        y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F - -10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 10.0F * Settings.scale);
        x -= (float) img.packedWidth / 2.0F;
        y -= (float) img.packedHeight / 2.0F;
        vY = .1f;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        rotation = MathUtils.random(-8.0F, 8.0F);
    }
    
    public void update()
    {
        if (delay > 0)
        {
            delay -= Gdx.graphics.getDeltaTime();
        }
        else
        {
            if (duration > dur_div2)
            {
                color.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - dur_div2) / dur_div2);
            }
            else
            {
                color.a = Interpolation.fade.apply(0.0F, 1.0F, duration / dur_div2);
            }
            
            //vY += Gdx.graphics.getDeltaTime() * (vY * 0.15f) * Settings.scale;
            vY += (vY * acceleration) * Settings.scale;
            duration -= Gdx.graphics.getDeltaTime();
            if (duration < 0.0F)
            {
                isDone = true;
            }
        }
    }
    
    public void render(SpriteBatch sb)
    {
        if (delay <= 0)
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(img, x, y + vY, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, scale * 0.8F, (0.1F + (dur_div2 * 2.0F - duration) * 2.0F * scale) * Settings.scale, rotation);
            sb.setBlendFunction(770, 771);
        }
    }
    
    public void dispose()
    {
    }
}
