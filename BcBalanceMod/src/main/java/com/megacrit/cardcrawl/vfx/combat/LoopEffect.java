package com.megacrit.cardcrawl.vfx.combat;

import bcBalanceMod.BcUtility;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class LoopEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float startingScale;
    private float endingScale;
    private float currentScale;
    private float startingAlpha;
    private float startingDuration;
    private TextureAtlas.AtlasRegion img;
    
    public LoopEffect(float delay)
    {
        try
        {
            if (BcUtility.getFilledOrbSlotCount() == 0)
            {
                isDone = true;
                return;
            }
            
            img = ImageMaster.CRYSTAL_IMPACT;
            
            startingDuration = .7f;
            this.duration = startingDuration + delay;
            
            startingAlpha = .5f;
            
            AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
            if (orb.ID == Lightning.ORB_ID)
            {
                color = new Color(1F, 1F, .6F, startingAlpha);
            }
            else if (orb.ID == Frost.ORB_ID)
            {
                color = new Color(.7F, .7F, 1F, startingAlpha);
            }
            else if (orb.ID == Dark.ORB_ID)
            {
                color = new Color(1F, .7F, 1F, startingAlpha);
            }
            else if (orb.ID == Plasma.ORB_ID)
            {
                color = new Color(1F, .5F, .5F, startingAlpha);
            }
            else
            {
                color = new Color(1F, 1F, 1F, 0);
            }
            
            startingScale = .3f;
            endingScale = .9f;
            currentScale = startingScale;
            
            renderBehind = true;
            updatePosition();
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
        }
    }
    
    void updatePosition()
    {
        AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
        x = orb.hb.cX  * Settings.scale;
        y = orb.hb.cY * Settings.scale;
        x -= (float) img.packedWidth / 2.0F;
        y -= (float) img.packedHeight / 2.0F;
    }
    
    public void update()
    {
        if (!isDone)
        {
            if (duration <= startingDuration)
            {
                float percentComplete = (startingDuration - duration) / startingDuration;
                float percentRemaining = duration / startingDuration;
                
                color.a = startingAlpha * percentRemaining;
                currentScale = startingScale + ((endingScale - startingScale) * percentComplete);
                
                updatePosition();
            }
            
            float deltaTime = Gdx.graphics.getDeltaTime();
            duration -= deltaTime;
            if (duration < 0.0F)
            {
                isDone = true;
            }
        }
    }
    
    public void render(SpriteBatch sb)
    {
        if (!isDone && (duration <= startingDuration))
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(
                img,
                x,
                y,
                (float) img.packedWidth / 2.0F,
                (float) img.packedHeight / 2.0F,
                (float) img.packedWidth,
                (float) img.packedHeight,
                currentScale,
                currentScale,
                rotation);
            
            sb.setBlendFunction(770, 771);
        }
    }
    
    public void dispose()
    {
    }
}
