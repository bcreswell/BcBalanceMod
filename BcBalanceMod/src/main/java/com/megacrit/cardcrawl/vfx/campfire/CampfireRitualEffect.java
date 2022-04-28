package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.*;

public class CampfireRitualEffect extends AbstractGameEffect
{
    private static final float DUR = 1.5F;
    private boolean openedScreen = false;
    private Color screenColor;
    
    public CampfireRitualEffect()
    {
        screenColor = AbstractDungeon.fadeColor.cpy();
        duration = 1.5F;
        screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }
    
    public void update()
    {
        if (!AbstractDungeon.isScreenUp)
        {
            duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }
        
        if (!AbstractDungeon.isScreenUp &&
                    !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() &&
                    AbstractDungeon.gridSelectScreen.forPurge)
        {
            AbstractCard curseToRemove = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardCrawlGame.metricData.addCampfireChoiceData("PURGE", curseToRemove.getMetricID());
            CardCrawlGame.sound.play("CARD_EXHAUST");
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curseToRemove, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(curseToRemove);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        
        if (duration < 1.0F && !openedScreen)
        {
            openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(
                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCurses()),
                    1,
                    "Select a Curse to Remove.",
                    false,
                    false,
                    true,
                    true);
        }
        
        if (duration < 0.0F)
        {
            isDone = true;
            
            if (CampfireUI.hidden)
            {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
        
    }
    
    private void updateBlackScreenColor()
    {
        if (duration > 1.0F)
        {
            screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 1.0F) * 2.0F);
        }
        else
        {
            screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, duration / 1.5F);
        }
    }
    
    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID)
        {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }
    
    public void dispose()
    {
    }
}
