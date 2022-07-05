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
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PowerTextEffect extends AbstractGameEffect
{
    private static final float TEXT_DURATION = 2.0F;
    private static final float STARTING_OFFSET_Y;
    private static final float TARGET_OFFSET_Y;
    private float x;
    private float y;
    private float offsetY;
    private float h;
    private String msg;
    private boolean spikeEffectTriggered = false;
    private TextureAtlas.AtlasRegion region;
    
    public PowerTextEffect(float x, float y, String msg, AbstractPower power)
    {
        duration = 2.0F;
        startingDuration = 2.0F;
        this.msg = msg;
        this.x = x - 64.0F * Settings.scale;
        this.y = y;
        color = Color.WHITE.cpy();
        offsetY = STARTING_OFFSET_Y;
        if (power != null)
        {
            this.region = power.region128;
        }
        scale = Settings.scale * 0.7F;
    }
    
    public void update()
    {
        if (duration < startingDuration * 0.8F && !spikeEffectTriggered && !Settings.DISABLE_EFFECTS)
        {
            spikeEffectTriggered = true;
            
            int i;
            for (i = 0; i < 10; ++i)
            {
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(x - MathUtils.random(20.0F) * Settings.scale + 70.0F * Settings.scale, y + MathUtils.random(STARTING_OFFSET_Y, TARGET_OFFSET_Y) * Settings.scale, 0.0F, MathUtils.random(50.0F, 400.0F) * Settings.scale, 0.0F, Settings.BLUE_TEXT_COLOR));
            }
            
            for (i = 0; i < 10; ++i)
            {
                AbstractDungeon.effectsQueue.add(new FlyingSpikeEffect(x + MathUtils.random(20.0F) * Settings.scale, y + MathUtils.random(STARTING_OFFSET_Y, TARGET_OFFSET_Y) * Settings.scale, 0.0F, MathUtils.random(-400.0F, -50.0F) * Settings.scale, 0.0F, Settings.BLUE_TEXT_COLOR));
            }
        }
        
        offsetY = Interpolation.exp10In.apply(TARGET_OFFSET_Y, STARTING_OFFSET_Y, duration / 2.0F);
        color.a = Interpolation.exp10Out.apply(0.0F, 1.0F, duration / 2.0F);
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F)
        {
            isDone = true;
            duration = 0.0F;
        }
        
    }
    
    public void render(SpriteBatch sb)
    {
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.losePowerFont, msg, x, y + offsetY, color);
        if (region != null)
        {
            sb.setColor(color);
            sb.setBlendFunction(770, 1);
            sb.draw(region, x - (float) (region.packedWidth / 2) - 64.0F * Settings.scale, y + h + offsetY - (float) (region.packedHeight / 2) - 30.0F * Settings.scale, (float) region.packedWidth / 2.0F, (float) region.packedHeight / 2.0F, (float) region.packedWidth, (float) region.packedHeight, scale, scale, rotation);
            sb.setBlendFunction(770, 771);
        }
        
    }
    
    public void dispose()
    {
    }
    
    static
    {
        STARTING_OFFSET_Y = 80.0F * Settings.scale;
        TARGET_OFFSET_Y = 160.0F * Settings.scale;
    }
}
