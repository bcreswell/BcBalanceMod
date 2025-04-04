
package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DieDieDieEffect extends AbstractGameEffect 
{
    private float x;
    private float y;
    private Color color2;
    private int variation;

    public DieDieDieEffect(float x, float y, Color color1, Color color2, int variation) {
        this.x = x;
        this.y = y;
        this.color = color1;
        this.color2 = color2;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.variation = variation;
    }

    public void update()
    {
        if (MathUtils.randomBoolean())
        {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        }
        else
        {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        }
        
        switch (variation)
        {
            case 0:
                AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x - 50.0F, y , 250.0F, -250.0F, -135.0F, color, color2));
                break;
            case 1:
                AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x + 50, y, -250.0F, -250.0F, 135.0F, color, color2));
                break;
            case 2:
            default:
                AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(x , y, 0, -300.0F, 180F, color, color2));
                break;
        }
        isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

