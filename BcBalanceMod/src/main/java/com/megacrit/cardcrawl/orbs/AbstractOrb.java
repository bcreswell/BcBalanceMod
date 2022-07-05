package com.megacrit.cardcrawl.orbs;

import bcBalanceMod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;

import java.util.ArrayList;

public abstract class AbstractOrb
{
    public String name;
    public String description;
    public String ID;
    protected ArrayList<PowerTip> tips = new ArrayList();
    public int evokeAmount = 0;
    public int passiveAmount = 0;
    protected int baseEvokeAmount = 0;
    protected int basePassiveAmount = 0;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected Color evokeColor;
    protected boolean showEvokeInsteadOfPassive;
    protected boolean hidePassiveValue;
    protected boolean hideEvokeUnlessShown;
    protected static final int W = 96;
    public Hitbox hb;
    protected Texture img;
    protected BobEffect bobEffect;
    protected static final float NUM_X_OFFSET;
    protected static final float NUM_Y_OFFSET;
    protected float angle;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    public int evokeTimes;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;
    
    public AbstractOrb()
    {
        c = Settings.CREAM_COLOR.cpy();
        shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        img = null;
        bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        fontScale = 0.7F;
        showEvokeValue = false;
        channelAnimTimer = 0.5F;
        evokeTimes = 1;
        evokeColor = new Color(1, 1, 1, 1);
    }
    
    public abstract void updateDescription();
    
    public abstract void onEvoke();
    
    public static AbstractOrb getRandomOrb(boolean useCardRng)
    {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new Dark());
        orbs.add(new Frost());
        orbs.add(new Lightning());
        orbs.add(new Plasma());
        return useCardRng ? (AbstractOrb) orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)) : (AbstractOrb) orbs.get(MathUtils.random(orbs.size() - 1));
    }
    
    public void onStartOfTurn()
    {
    }
    
    public void onEndOfTurn()
    {
    }
    
    public void applyFocus()
    {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if (power != null && !ID.equals("Plasma"))
        {
            passiveAmount = Math.max(0, basePassiveAmount + power.amount);
            evokeAmount = Math.max(0, baseEvokeAmount + power.amount);
        }
        else
        {
            passiveAmount = basePassiveAmount;
            evokeAmount = baseEvokeAmount;
        }
        
    }
    
    public static int applyLockOn(AbstractCreature target, int dmg)
    {
        int retVal = dmg;
        if (target.hasPower("Lockon"))
        {
            retVal = (int) ((float) dmg * 1.5F);
        }
        
        return retVal;
    }
    
    public abstract AbstractOrb makeCopy();
    
    public void update()
    {
        hb.update();
        if (hb.hovered)
        {
            TipHelper.renderGenericTip(tX + 96.0F * Settings.scale, tY + 64.0F * Settings.scale, name, description);
        }
        
        fontScale = MathHelper.scaleLerpSnap(fontScale, 0.7F);
    }
    
    public void updateAnimation()
    {
        bobEffect.update();
        cX = MathHelper.orbLerpSnap(cX, AbstractDungeon.player.animX + tX);
        cY = MathHelper.orbLerpSnap(cY, AbstractDungeon.player.animY + tY);
        if (channelAnimTimer != 0.0F)
        {
            channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (channelAnimTimer < 0.0F)
            {
                channelAnimTimer = 0.0F;
            }
        }
        
        c.a = Interpolation.pow2In.apply(1.0F, 0.01F, channelAnimTimer / 0.5F);
        scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, channelAnimTimer / 0.5F);
    }
    
    public void setSlot(int slotNum, int maxOrbs)
    {
        float dist = 160.0F * Settings.scale + (float) maxOrbs * 10.0F * Settings.scale;
        float angle = 100.0F + (float) maxOrbs * 12.0F;
        float offsetAngle = angle / 2.0F;
        angle *= (float) slotNum / ((float) maxOrbs - 1.0F);
        angle += 90.0F - offsetAngle;
        tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
        tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        if (maxOrbs == 1)
        {
            tX = AbstractDungeon.player.drawX;
            tY = 160.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        }
        
        hb.move(tX, tY);
    }
    
    public abstract void render(SpriteBatch var1);
    
    protected void renderText(SpriteBatch sb)
    {
        int mainValue = showEvokeInsteadOfPassive ? evokeAmount : passiveAmount;
        
        float xOffset = 0;
        float yOffset = 0;
        float scale = 1;
        
        boolean showEvokeText = false;
        if ((evokeAmount > 0) &&
                    (evokeTimes > 0) &&
                    (showEvokeValue || !hideEvokeUnlessShown))
        {
            showEvokeText = true;
        }
        
        if (showEvokeInsteadOfPassive && !showEvokeValue)
        {
            showEvokeText = false;
        }
        
        boolean showMainValueText = true;
        if ((mainValue <= 0) ||
                    showEvokeValue ||
                    hidePassiveValue )
        {
            showMainValueText = false;
        }
        
        if (showMainValueText)
        {
            FontHelper.renderFontCentered(
                    sb,
                    FontHelper.cardEnergyFont_L,
                    Integer.toString(mainValue),
                    cX + NUM_X_OFFSET + xOffset,
                    cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + yOffset,
                    c,
                    fontScale * scale);
        }
        
        scale = .8f;
        
        xOffset = 16.0F * Settings.scale;
        yOffset = 24.0F * Settings.scale;
        
        if (showEvokeValue)
        {
            scale = 2f;
            xOffset += 2.0F * Settings.scale;
            yOffset += 4.0F * Settings.scale;
        }
        
        if (showEvokeText)
        {
            String evokeString = Integer.toString(evokeAmount);
            if (evokeTimes > 1)
            {
                //int evokeFinalAmount = evokeAmount * evokeTimes;
                //evokeString += " x " + evokeTimes + " = " + evokeFinalAmount;
                //evokeString =  Integer.toString(evokeFinalAmount);
                scale = 1.4f;
                evokeString += " x" + evokeTimes;
                xOffset += 25.0F * Settings.scale;
            }
            
            FontHelper.renderFontCentered(
                    sb,
                    FontHelper.cardEnergyFont_L,
                    evokeString,
                    cX + NUM_X_OFFSET + xOffset,
                    cY + bobEffect.y / 2.0F + NUM_Y_OFFSET + yOffset,
                    evokeColor,
                    fontScale * scale);
        }
    }
    
    public void triggerEvokeAnimation()
    {
    }
    
    public void showEvokeValue()
    {
        showEvokeValue = true;
        //fontScale = 1.5F;
    }
    
    public void hideEvokeValues()
    {
        showEvokeValue = false;
        evokeTimes = 1;
    }
    
    public abstract void playChannelSFX();
    
    static
    {
        NUM_X_OFFSET = 20.0F * Settings.scale;
        NUM_Y_OFFSET = -12.0F * Settings.scale;
    }
}