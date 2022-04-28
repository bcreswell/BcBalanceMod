package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HiddenShivTrailParticle extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private Vector2 pos;
    private Vector2 velocity;
    private float rotation;
    
    public HiddenShivTrailParticle(Vector2 startPos, Vector2 velocity, float rotation)
    {
        img = ImageMaster.STRIKE_LINE;
        pos = startPos;
        this.velocity = velocity;
        velocity.scl(.45f);
        this.rotation = rotation;
        
        color = new Color(1.0F, 1.0F, 1.0F, .4F);
        duration = .5F;
        scale = .6f;
        renderBehind = MathUtils.randomBoolean();
    }
    
    public void update()
    {
        if (!isDone)
        {
            pos.add(velocity);
            color.a *= .90f;
            scale *= .97f;
            
            duration -= Gdx.graphics.getDeltaTime();
            if (duration < 0.0F)
            {
                isDone = true;
            }
        }
    }
    
    public void render(SpriteBatch sb)
    {
        if (!this.isDone)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(color);
            
            sb.draw(
                    this.img,
                    pos.x - (float) (this.img.packedWidth / 2),
                    pos.y - (float) (this.img.packedHeight / 2),
                    (float) this.img.packedWidth / 2.0F,
                    (float) this.img.packedHeight / 2.0F,
                    (float) this.img.packedWidth, (float) this.img.packedHeight,
                    scale,
                    scale,
                    this.rotation);
            
            sb.setBlendFunction(770, 771);
        }
    }
    
    public void dispose()
    {
    }
}
