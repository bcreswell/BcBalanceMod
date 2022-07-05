package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HiddenShivEffect extends AbstractGameEffect
{
    public static final float ShivFlyingDuration = .1f;
    public static final float Speed = 130f * Settings.scale;
    
    private float sourceX;
    private float sourceY;
    private float targetX;
    private float targetY;
    boolean firstUpdate = true;
    HiddenShivParticle shivParticle;
    
    public HiddenShivEffect(AbstractCreature source, AbstractCreature target)
    {
        sourceX = source.hb.cX;
        sourceY = source.hb.cY;
        
        //pick a random place within the target's hitbox
        targetX = target.hb.cX + MathUtils.random(-target.hb.width / 4f, target.hb.width / 4f);
        targetY = target.hb.cY + MathUtils.random(-target.hb.height / 4f, target.hb.height / 4f);
        
        scale = 0.12F;
        duration = 0.5F;
    }
    
    public void update()
    {
        if (firstUpdate)
        {
            firstUpdate = false;
            Vector2 startPos = new Vector2(
                    this.sourceX + MathUtils.random(60.0F, -60.0F) * Settings.scale,
                    this.sourceY + MathUtils.random(60.0F, -60.0F) * Settings.scale);
            Vector2 targetPos = new Vector2(targetX, targetY);
            shivParticle = new HiddenShivParticle(new Vector2(startPos), targetPos);
            AbstractDungeon.effectsQueue.add(shivParticle);

//            float s = 1f;
//            Vector2 reverseDirection = new Vector2(startPos);
//            reverseDirection.sub(targetPos);
//            reverseDirection.nor();
//            reverseDirection.scl(2);
//            for (int i = 0; i < 10; i++)
//            {
//                AbstractDungeon.effectsQueue.add(new HiddenShivParticle(new Vector2(startPos), targetPos, s));
//                s -= .04f;
//                startPos.add(reverseDirection);
//            }
        }
        else if (shivParticle.isDone)
        {
            isDone = true;
        }
        else
        {
            HiddenShivTrailParticle trailParticle = new HiddenShivTrailParticle(
                    new Vector2(shivParticle.pos),
                    new Vector2(shivParticle.velocity),
                    shivParticle.rotation);
            AbstractDungeon.effectsQueue.add(trailParticle);
        }
        
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
        {
            this.isDone = true;
        }
    }
    
    public void render(SpriteBatch sb)
    {
    }
    
    public void dispose()
    {
    }
}
