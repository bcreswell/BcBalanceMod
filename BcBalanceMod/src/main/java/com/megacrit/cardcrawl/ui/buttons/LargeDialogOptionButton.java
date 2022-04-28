package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LargeDialogOptionButton {
    private static final float OPTION_SPACING_Y;
    private static final float TEXT_ADJUST_X;
    private static final float TEXT_ADJUST_Y;
    public String msg;
    private Color textColor;
    private Color boxColor;
    private float x;
    private float y;
    public Hitbox hb;
    private static final float ANIM_TIME = 0.5F;
    private float animTimer;
    private float alpha;
    private static final Color TEXT_ACTIVE_COLOR;
    private static final Color TEXT_INACTIVE_COLOR;
    private static final Color TEXT_DISABLED_COLOR;
    private Color boxInactiveColor;
    public boolean pressed;
    public boolean isDisabled;
    public int slot;
    private AbstractCard cardToPreview;
    private AbstractRelic relicToPreview;
    private static final int W = 890;
    private static final int H = 77;
    
    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractCard previewCard, AbstractRelic previewRelic) {
        this.textColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.boxColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.y = -9999.0F * Settings.scale;
        this.animTimer = 0.5F;
        this.alpha = 0.0F;
        this.boxInactiveColor = new Color(0.2F, 0.25F, 0.25F, 0.0F);
        this.pressed = false;
        this.slot = 0;
        switch(AbstractEvent.type) {
            case TEXT:
                this.x = 895.0F * Settings.xScale;
                break;
            case IMAGE:
                this.x = 1260.0F * Settings.xScale;
                break;
            case ROOM:
                this.x = 620.0F * Settings.xScale;
        }
        
        this.slot = slot;
        this.isDisabled = isDisabled;
        this.cardToPreview = previewCard;
        this.relicToPreview = previewRelic;
        if (isDisabled) {
            this.msg = this.stripColor(msg);
        } else {
            this.msg = msg;
        }
        
        this.hb = new Hitbox(892.0F * Settings.xScale, 80.0F * Settings.yScale);
    }
    
    public LargeDialogOptionButton(int slot, String msg, AbstractCard previewCard, AbstractRelic previewRelic) {
        this(slot, msg, false, previewCard, previewRelic);
    }
    
    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractCard previewCard) {
        this(slot, msg, isDisabled, previewCard, (AbstractRelic)null);
    }
    
    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled, AbstractRelic previewRelic) {
        this(slot, msg, isDisabled, (AbstractCard)null, previewRelic);
    }
    
    public LargeDialogOptionButton(int slot, String msg) {
        this(slot, msg, false, (AbstractCard)null, (AbstractRelic)null);
    }
    
    public LargeDialogOptionButton(int slot, String msg, boolean isDisabled) {
        this(slot, msg, isDisabled, (AbstractCard)null, (AbstractRelic)null);
    }
    
    public LargeDialogOptionButton(int slot, String msg, AbstractCard previewCard) {
        this(slot, msg, false, previewCard);
    }
    
    public LargeDialogOptionButton(int slot, String msg, AbstractRelic previewRelic) {
        this(slot, msg, false, previewRelic);
    }
    
    private String stripColor(String input) {
        input = input.replace("#r", "");
        input = input.replace("#g", "");
        input = input.replace("#b", "");
        input = input.replace("#y", "");
        input = input.replace("#l", "");
        return input;
    }
    
    public void calculateY(int size) {
        if (AbstractEvent.type != AbstractEvent.EventType.ROOM) {
            this.y = Settings.OPTION_Y - 424.0F * Settings.scale;
            this.y += (float)this.slot * OPTION_SPACING_Y;
            this.y -= (float)size * OPTION_SPACING_Y;
        } else {
            this.y = Settings.OPTION_Y - 500.0F * Settings.scale;
            this.y += (float)this.slot * OPTION_SPACING_Y;
            this.y -= (float)RoomEventDialog.optionList.size() * OPTION_SPACING_Y;
        }
        
        this.hb.move(this.x, this.y);
    }
    
    public void update(int size) {
        this.calculateY(size);
        this.hoverAndClickLogic();
        this.updateAnimation();
    }
    
    private void updateAnimation() {
        if (this.animTimer != 0.0F) {
            this.animTimer -= Gdx.graphics.getDeltaTime();
            if (this.animTimer < 0.0F) {
                this.animTimer = 0.0F;
            }
            
            this.alpha = Interpolation.exp5In.apply(0.0F, 1.0F, 1.0F - this.animTimer / 0.5F);
        }
        
        this.textColor.a = this.alpha;
        this.boxColor.a = this.alpha;
    }
    
    private void hoverAndClickLogic() {
        this.hb.update();
        if (this.hb.hovered && InputHelper.justClickedLeft && !this.isDisabled && this.animTimer < 0.1F) {
            InputHelper.justClickedLeft = false;
            this.hb.clickStarted = true;
        }
        
        if (this.hb.hovered && CInputActionSet.select.isJustPressed() && !this.isDisabled) {
            this.hb.clicked = true;
        }
        
        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.pressed = true;
        }
        
        if (!this.isDisabled) {
            if (this.hb.hovered) {
                this.textColor = TEXT_ACTIVE_COLOR;
                this.boxColor = Color.WHITE.cpy();
            } else {
                this.textColor = TEXT_INACTIVE_COLOR;
                this.boxColor = new Color(0.4F, 0.4F, 0.4F, 1.0F);
            }
        } else {
            this.textColor = TEXT_DISABLED_COLOR;
            this.boxColor = this.boxInactiveColor;
        }
        
        if (this.hb.hovered) {
            if (!this.isDisabled) {
                this.textColor = TEXT_ACTIVE_COLOR;
            } else {
                this.textColor = Color.GRAY.cpy();
            }
        } else if (!this.isDisabled) {
            this.textColor = TEXT_ACTIVE_COLOR;
        } else {
            this.textColor = Color.GRAY.cpy();
        }
        
    }
    
    public void render(SpriteBatch sb) {
        float scale = Settings.scale;
        float xScale = Settings.xScale;
        if (this.hb.clickStarted) {
            scale *= 0.99F;
            xScale *= 0.99F;
        }
        
        if (this.isDisabled) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.EVENT_BUTTON_DISABLED, this.x - 445.0F, this.y - 38.5F, 445.0F, 38.5F, 890.0F, 77.0F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
        } else {
            sb.setColor(this.boxColor);
            sb.draw(ImageMaster.EVENT_BUTTON_ENABLED, this.x - 445.0F, this.y - 38.5F, 445.0F, 38.5F, 890.0F, 77.0F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.15F));
            sb.draw(ImageMaster.EVENT_BUTTON_ENABLED, this.x - 445.0F, this.y - 38.5F, 445.0F, 38.5F, 890.0F, 77.0F, xScale, scale, 0.0F, 0, 0, 890, 77, false, false);
            sb.setBlendFunction(770, 771);
        }
        
        if (FontHelper.getSmartWidth(FontHelper.largeDialogOptionFont, this.msg, (float)Settings.WIDTH, 0.0F) > 800.0F * Settings.xScale) {
            FontHelper.renderSmartText(sb, FontHelper.smallDialogOptionFont, this.msg, this.x + TEXT_ADJUST_X, this.y + TEXT_ADJUST_Y, (float)Settings.WIDTH, 0.0F, this.textColor);
        } else {
            FontHelper.renderSmartText(sb, FontHelper.largeDialogOptionFont, this.msg, this.x + TEXT_ADJUST_X, this.y + TEXT_ADJUST_Y, (float)Settings.WIDTH, 0.0F, this.textColor);
        }
        
        this.hb.render(sb);
    }
    
    public void renderCardPreview(SpriteBatch sb) {
        if (this.cardToPreview != null && this.hb.hovered) {
            this.cardToPreview.current_x = this.x + this.hb.width / 1.75F;
            if (this.y < this.cardToPreview.hb.height / 2.0F + 5.0F) {
                this.y = this.cardToPreview.hb.height / 2.0F + 5.0F;
            }
            
            this.cardToPreview.current_y = this.y;
            this.cardToPreview.render(sb);
        }
        
    }
    
    public void renderRelicPreview(SpriteBatch sb) {
        if (!Settings.isControllerMode && this.relicToPreview != null && this.hb.hovered) {
           
            float tx = this.x - 650;
            if (tx < 100)
            {
                tx = (this.x + this.hb.width / 1.75F) - 20;

                if (cardToPreview != null)
                {
                    //bump the tooltip over a little further to be next to the card
                    tx += 170;
                }
            }
            
            TipHelper.queuePowerTips(tx, (float)InputHelper.mY + TipHelper.calculateToAvoidOffscreen(this.relicToPreview.tips, (float)InputHelper.mY), this.relicToPreview.tips);
        }
        
    }
    
    static {
        OPTION_SPACING_Y = -82.0F * Settings.scale;
        TEXT_ADJUST_X = -400.0F * Settings.xScale;
        TEXT_ADJUST_Y = 10.0F * Settings.scale;
        TEXT_ACTIVE_COLOR = Color.WHITE.cpy();
        TEXT_INACTIVE_COLOR = new Color(0.8F, 0.8F, 0.8F, 1.0F);
        TEXT_DISABLED_COLOR = Color.FIREBRICK.cpy();
    }
}
