package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ShineSparkleEffect;

public class FlickCoinEffect extends AbstractGameEffect
{
    private static TextureAtlas.AtlasRegion img;
    private float sourceX;
    private float sourceY;
    private float currentX;
    private float currentY;
    private float destinationX;
    private float destinationY;
    private float yOffset;
    private float bounceHeight;
    private static final float DUR = 0.5F;
    private boolean playedSfx = false;
    private float sparkleTimer = 0.0F;
    float maxDuration;
    float halfMaxDuration;
    
    public FlickCoinEffect(float srcX, float srcY, float destX, float destY, float duration)
    {
        if (img == null)
        {
            img = ImageMaster.vfxAtlas.findRegion("combat/empowerCircle1");
        }
        
        sourceX = srcX;
        sourceY = srcY;
        currentX = sourceX;
        currentY = sourceY;
        destinationX = destX;
        destinationY = destY - 100.0F * Settings.scale;
        rotation = 0.0F;
        maxDuration = duration;
        this.duration = maxDuration;
        halfMaxDuration = (maxDuration / 2f);
        color = new Color(1.0F, 1.0F, 0.0F, 0.0F);
        if (sourceY > destinationY)
        {
            bounceHeight = 500f * Settings.scale;
        }
        else
        {
            bounceHeight = destinationY - sourceY + 500f * Settings.scale;
        }
    }
    
    public void update()
    {
        if (!playedSfx)
        {
            playedSfx = true;
            CardCrawlGame.sound.playA("ATTACK_WHIFF_2", MathUtils.random(0.7F, 0.8F));
        }
        
        sparkleTimer -= Gdx.graphics.getDeltaTime();
        if (duration < 0.4F && sparkleTimer < 0.0F)
        {
            for (int i = 0; i < MathUtils.random(2, 5); ++i)
            {
                AbstractDungeon.effectsQueue.add(new ShineSparkleEffect(currentX, currentY + yOffset));
            }
            
            sparkleTimer = MathUtils.random(0.05F, 0.1F);
        }
        
        currentX = Interpolation.linear.apply(destinationX, sourceX, duration / maxDuration);
        currentY = Interpolation.linear.apply(destinationY, sourceY, duration / maxDuration);
        if (destinationX > sourceX)
        {
            rotation -= Gdx.graphics.getDeltaTime() * 1000.0F;
        }
        else
        {
            rotation += Gdx.graphics.getDeltaTime() * 1000.0F;
        }
        
        if (duration > halfMaxDuration)
        {
            color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (duration - halfMaxDuration) / halfMaxDuration) * Settings.scale;
            yOffset = Interpolation.circleIn.apply(bounceHeight, 0.0F, (duration - halfMaxDuration) / halfMaxDuration) * Settings.scale;
        }
        else
        {
            yOffset = Interpolation.circleOut.apply(0.0F, bounceHeight, duration / halfMaxDuration) * Settings.scale;
        }
        
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F)
        {
            isDone = true;
            CardCrawlGame.sound.playA("GOLD_GAIN", MathUtils.random(0.0F, 0.1F));
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(destinationX, destinationY + 100.0F * Settings.scale, Color.GOLD.cpy()));
        }
    }
    
    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.4F, 1.0F, 1.0F, color.a / 5.0F));
        sb.setColor(color);
        sb.draw(img, currentX - (float) (img.packedWidth / 2), currentY - (float) (img.packedHeight / 2) + yOffset, (float) img.packedWidth / 2.0F, (float) img.packedHeight / 2.0F, (float) img.packedWidth, (float) img.packedHeight, scale * 0.7F, scale * 0.4F, rotation);
        sb.setBlendFunction(770, 771);
    }
    
    public void dispose()
    {
    }
}
