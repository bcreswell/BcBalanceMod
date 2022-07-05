package com.megacrit.cardcrawl.vfx.cardManip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.Iterator;

public class ShowCardBrieflyEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 3.5f;
    private AbstractCard card;
    private static final float PADDING;
    
    public ShowCardBrieflyEffect(AbstractCard card) {
        this.card = card;
        this.duration = EFFECT_DUR;
        this.startingDuration = EFFECT_DUR;
        this.identifySpawnLocation((float)Settings.WIDTH - 96.0F * Settings.scale, (float)Settings.HEIGHT - 32.0F * Settings.scale);
        card.drawScale = 0.01F;
        card.targetDrawScale = 1.0F;
    }
    
    public ShowCardBrieflyEffect(AbstractCard card, float x, float y) {
        this.card = card;
        this.duration = EFFECT_DUR;
        this.startingDuration = EFFECT_DUR;
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        this.card.current_x = (float)Settings.WIDTH / 2.0F;
        this.card.current_y = (float)Settings.HEIGHT / 2.0F;
        this.card.target_x = x;
        this.card.target_y = y;
    }
    
    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        Iterator var4 = AbstractDungeon.effectList.iterator();
        
        while(var4.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect)var4.next();
            if (e instanceof ShowCardBrieflyEffect) {
                ++effectCount;
            }
        }
        
        this.card.current_x = (float)Settings.WIDTH / 2.0F;
        this.card.current_y = (float)Settings.HEIGHT / 2.0F;
        this.card.target_y = (float)Settings.HEIGHT * 0.5F;
        switch(effectCount) {
            case 0:
                this.card.target_x = (float)Settings.WIDTH * 0.5F;
                break;
            case 1:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                break;
            case 2:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                break;
            case 3:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            case 4:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            default:
                this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
        }
        
    }
    
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.6F) {
            this.card.fadingOut = true;
        }
        
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        
    }
    
    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
        
    }
    
    public void dispose() {
    }
    
    static {
        PADDING = 30.0F * Settings.scale;
    }
}
