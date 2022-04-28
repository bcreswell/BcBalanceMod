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

public class HiddenShivParticle extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    public Vector2 pos;
    public Vector2 direction;
    public Vector2 velocity;
    public Vector2 target;
    public float rotation;
    public static final float DistanceThreshold = HiddenShivEffect.Speed * 1.1f;
    
    public HiddenShivParticle(Vector2 startPos, Vector2 targetPos)
    {
        this(startPos, targetPos, new Color(1.0F, 1.0F, 1.0F, 0.9F));
    }
    
    public HiddenShivParticle(Vector2 startPos, Vector2 targetPos, Color color)
    {
        this.img = ImageMaster.THICK_3D_LINE;
        pos = startPos;
        target = targetPos;
        direction = new Vector2(targetPos);
        direction.sub(pos);
        direction.nor();
        this.rotation = direction.angle();
        
        velocity = new Vector2(direction);
        velocity.scl(HiddenShivEffect.Speed);
        
        this.color = color;
        this.duration = 5F;
        this.scale = .4f * Settings.scale;
        this.renderBehind = MathUtils.randomBoolean();
    }
    
    public void update()
    {
        this.updateMovement();
    }
    
    private void updateMovement()
    {
        pos.add(velocity);
        
        if (target.dst(pos) < DistanceThreshold)
        {
            //AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(target.x, target.y));
            
            //CardCrawlGame.sound.playAV("BLUNT_HEAVY", MathUtils.random(0.6F, 0.9F), 0.5F);
            //.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            this.isDone = true;
        }
        
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F)
        {
            this.isDone = true;
        }
    }
    
    public void render(SpriteBatch sb)
    {
        if (!this.isDone)
        {
            sb.setColor(Color.BLACK);
            float scaleCpy = this.scale;
            
            sb.draw(
                    this.img,
                    pos.x - (float) (this.img.packedWidth / 2),
                    pos.y - (float) (this.img.packedHeight / 2),
                    (float) this.img.packedWidth / 2.0F,
                    (float) this.img.packedHeight / 2.0F,
                    (float) this.img.packedWidth,
                    (float) this.img.packedHeight,
                    scaleCpy * 1.5F,
                    scaleCpy * 1.5F,
                    this.rotation);
            scaleCpy *= 0.98F;
            
            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            scaleCpy = this.scale;
            
            sb.draw(
                    this.img,
                    pos.x - (float) (this.img.packedWidth / 2),
                    pos.y - (float) (this.img.packedHeight / 2),
                    (float) this.img.packedWidth / 2.0F,
                    (float) this.img.packedHeight / 2.0F,
                    (float) this.img.packedWidth, (float) this.img.packedHeight,
                    scaleCpy,
                    scaleCpy,
                    this.rotation);
            scaleCpy *= 0.98F;
            
            sb.setBlendFunction(770, 771);
        }
    }
    
    public void dispose()
    {
    }
}
