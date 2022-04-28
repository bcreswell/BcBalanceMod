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

public class DemonFormParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private float vX;
    float pX;
    float pY;
    private float dur_div2;
    private TextureAtlas.AtlasRegion img;
    
    public DemonFormParticleEffect()
    {
        rerollImg();
        duration = MathUtils.random(0.6F, 1.5F);
        scale = MathUtils.random(0.6F, 1.0F) * Settings.scale;
        dur_div2 = duration / 2.0F;
        color = new Color(MathUtils.random(0.5F, 1.0F), MathUtils.random(0.2F, 0.5F), MathUtils.random(0.0F, 0.2F), 0.0F);
        float xOffset = MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 30.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 30.0F * Settings.scale);
        float yOffset = MathUtils.random(-110.0F * Settings.scale, -40.0F * Settings.scale);
        x = AbstractDungeon.player.hb.cX + xOffset;
        y = AbstractDungeon.player.hb.cY + yOffset;
        x -= (float) img.packedWidth / 2.0F;
        y -= (float) img.packedHeight / 2.0F;
        vX = xOffset * MathUtils.random(.0f, .3F);
        vY = 80.0F * Settings.scale;
        renderBehind = MathUtils.randomBoolean(0.2F + (scale - 0.5F));
        rotation = MathUtils.random(-8.0F, 8.0F);
    }
    
    void rerollImg()
    {
        switch (MathUtils.random(0, 2))
        {
            case 0:
                img = ImageMaster.FLAME_1;
                break;
            case 1:
                img = ImageMaster.FLAME_2;
                break;
            case 2:
                img = ImageMaster.FLAME_3;
                break;
        }
    }
    
    public void update()
    {
        if (duration > dur_div2)
        {
            color.a = Interpolation.fade.apply(0.4F, 0.0F, (duration - dur_div2) / dur_div2);
        }
        else
        {
            color.a = Interpolation.fade.apply(0.0F, 0.4F, duration / dur_div2);
        }
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        if (MathUtils.random(0,20) == 0)
        {
            rerollImg();
        }
        
        vY *= 1.02f;
        pX += deltaTime * vX;
        pY += deltaTime * vY;
        
        duration -= deltaTime;
        if (duration < 0.0F)
        {
            isDone = true;
        }
    }
    
    public void render(SpriteBatch sb)
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
    
    public void dispose()
    {
    }
}
