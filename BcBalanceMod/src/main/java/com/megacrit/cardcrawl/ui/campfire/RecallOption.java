package com.megacrit.cardcrawl.ui.campfire;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.campfire.CampfireRecallEffect;

public class RecallOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private float scale;
    private float normScale;
    private final float SHDW_X;
    private final float SHDW_Y;
    private Color color;
    private Color descriptionColor;
    
    public RecallOption()
    {
        this.label = TEXT[0];
        this.description = TEXT[1];
        this.img = ImageMaster.CAMPFIRE_RECALL_BUTTON;
        normScale = 0.9F * Settings.scale;
        SHDW_X = 11.0F * Settings.scale;
        SHDW_Y = -8.0F * Settings.scale;
        scale = normScale;
        color = Color.WHITE.cpy();
        descriptionColor = Settings.CREAM_COLOR.cpy();
    }
    
    public void useOption()
    {
        AbstractDungeon.effectList.add(new CampfireRecallEffect());
    }
    
    //copied from base class. only change is to modify text color when its the last chance to recall.
    public void render(SpriteBatch sb)
    {
        float scaler = (this.scale - normScale) * 10.0F / Settings.scale;
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, this.color.a / 5.0F));
        sb.draw(this.img, this.hb.cX - 128.0F + SHDW_X, this.hb.cY - 128.0F + SHDW_Y, 128.0F, 128.0F, 256.0F, 256.0F, this.scale, this.scale, 0.0F, 0, 0, 256, 256, false, false);
        sb.setColor(new Color(1.0F, 0.93F, 0.45F, scaler));
        sb.draw(ImageMaster.CAMPFIRE_HOVER_BUTTON, this.hb.cX - 128.0F, this.hb.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.scale * 1.075F, this.scale * 1.075F, 0.0F, 0, 0, 256, 256, false, false);
        sb.setColor(this.color);
        if (!this.usable)
        {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);
        }
        
        sb.draw(this.img, this.hb.cX - 128.0F, this.hb.cY - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.scale, this.scale, 0.0F, 0, 0, 256, 256, false, false);
        if (!this.usable)
        {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        }
        
        if (!this.usable)
        {
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, this.label, this.hb.cX, this.hb.cY - 60.0F * Settings.scale - 50.0F * Settings.scale * (this.scale / Settings.scale), Color.LIGHT_GRAY);
        }
        else
        {
            Color labelColor = Settings.GOLD_COLOR;
            if ((AbstractDungeon.floorNum >= 49) && (AbstractDungeon.actNum >=3) && !Settings.hasRubyKey)
            {
                //last chance!
                labelColor = Settings.PURPLE_COLOR;
            }
            
            FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, this.label, this.hb.cX, this.hb.cY - 60.0F * Settings.scale - 50.0F * Settings.scale * (this.scale / Settings.scale), labelColor);
        }
        
        this.descriptionColor.a = scaler;
        FontHelper.renderFontCenteredTopAligned(sb, FontHelper.topPanelInfoFont, this.description, 950.0F * Settings.xScale, (float) Settings.HEIGHT / 2.0F + 20.0F * Settings.scale, this.descriptionColor);
        this.hb.render(sb);
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Recall Option");
        TEXT = uiStrings.TEXT;
    }
}
