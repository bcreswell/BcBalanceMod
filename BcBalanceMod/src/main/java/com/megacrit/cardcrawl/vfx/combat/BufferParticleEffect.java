package com.megacrit.cardcrawl.vfx.combat;

import bcBalanceMod.BcUtility;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BufferParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float vX;
    float pX;
    float pY;
    private float dur_div2;
    private TextureAtlas.AtlasRegion img;
    float fullAlpha;
    
    public BufferParticleEffect(AbstractCreature owner)
    {
        try
        {
            img = ImageMaster.GLOW_SPARK;
            duration = MathUtils.random(0.6F, .9F);
            scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;
            dur_div2 = duration / 2.0F;
            color = new Color(1, 1, 1, 0);
            fullAlpha = .7f;
            rerollColor();
            float xOffset = MathUtils.random(-owner.hb.width / 2.0F - 10.0F * Settings.scale, owner.hb.width / 2.0F + 10.0F * Settings.scale);
            float yOffset = MathUtils.random(-130.0F * Settings.scale, -50.0F * Settings.scale);
            x = owner.hb.cX + xOffset;
            y = owner.hb.cY + yOffset;
            x -= (float) img.packedWidth / 2.0F;
            y -= (float) img.packedHeight / 2.0F;
            vX = xOffset * MathUtils.random(.0f, .3F);
            vY = MathUtils.random(60F, 150F) * Settings.scale;
            renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
            //rotation = MathUtils.random(-8.0F, 8.0F);
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
    
    public void update()
    {
        try
        {
            if (duration > dur_div2)
            {
                color.a = Interpolation.fade.apply(fullAlpha, 0.0F, (duration - dur_div2) / dur_div2);
            }
            else
            {
                color.a = Interpolation.fade.apply(0.0F, fullAlpha, duration / dur_div2);
            }
            float deltaTime = Gdx.graphics.getDeltaTime();
            
            if (MathUtils.random(0, 20) == 0)
            {
                rerollColor();
            }
            
            vY *= 1.02f;
            //pX += deltaTime * vX;
            pY += deltaTime * vY;
            
            duration -= deltaTime;
            if (duration < 0.0F)
            {
                isDone = true;
            }
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
    
    void rerollColor()
    {
        try
        {
            color.r = MathUtils.random(0.4F, .7F);
            color.g = MathUtils.random(0.4F, .7F);
            color.b = MathUtils.random(0.6F, 1F);
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
    
    public void render(SpriteBatch sb)
    {
        try
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(
                    img,
                    x + pX,
                    y + pY,
                    (float) img.packedWidth / 2.0F,
                    (float) img.packedHeight / 2.0F,
                    (float) img.packedWidth,
                    (float) img.packedHeight,
                    scale * 0.6F,
                    scale * 0.6F,//(0.1F + (dur_div2 * 2.0F - duration) * 2.8F * scale) * Settings.scale,
                    rotation);
            
            sb.setBlendFunction(770, 771);
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
    
    public void dispose()
    {
    }
}
