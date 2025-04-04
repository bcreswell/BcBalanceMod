//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;

public class SortHeaderButton {
    public Hitbox hb;
    public boolean isAscending;
    public boolean isActive;
    private String text;
    public SortHeaderButtonListener delegate;
    private final float ARROW_SIZE;
    public float textWidth;

    public SortHeaderButton(String text, float cx, float cy) {
        this.isAscending = false;
        this.isActive = false;
        this.ARROW_SIZE = 32.0F;
        this.hb = new Hitbox(210.0F * Settings.xScale, 48.0F * Settings.scale);
        this.hb.move(cx, cy);
        this.text = text;
        this.textWidth = FontHelper.getSmartWidth(FontHelper.topPanelInfoFont, text, Float.MAX_VALUE, 0.0F);
    }

    public SortHeaderButton(String text, float cx, float cy, SortHeaderButtonListener delegate) {
        this(text, cx, cy);
        this.delegate = delegate;
    }

    public void update() {
        this.hb.update();
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        }

        if (this.hb.hovered && InputHelper.justClickedLeft) {
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked || this.hb.hovered && CInputActionSet.select.isJustPressed()) {
            this.hb.clicked = false;
            if (!isActive)
            {
                isActive = true;
                isAscending = true;
            }
            else if (isActive && isAscending)
            {
                isAscending = false;
            }
            else if (isActive && !isAscending)
            {
                isActive = false;
            }
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.2F);
            if (this.delegate instanceof CardLibSortHeader) {
                ((CardLibSortHeader)this.delegate).clearActiveButtons();
            }

            this.delegate.didChangeOrder(this, this.isAscending);
        }

    }

    public void updateScrollPosition(float newY) {
        this.hb.move(this.hb.cX, newY);
    }

    public void render(SpriteBatch sb) {
        Color color = !this.hb.hovered ? Settings.CREAM_COLOR : Settings.GOLD_COLOR;
        FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, this.text, this.hb.cX, this.hb.cY, color);
        sb.setColor(color);
        if (isActive)
        {
            float arrowCenterOffset = 16.0F;
            sb.draw(ImageMaster.FILTER_ARROW, this.hb.cX - 16.0F + this.textWidth / 2.0F + 16.0F * Settings.xScale, this.hb.cY - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 32, 32, false, !this.isAscending);
        }
        this.hb.render(sb);
    }

    public void reset() {
        this.isAscending = false;
    }

    public void setActive(boolean isActive) {
        //this.isActive = isActive;
    }
}
