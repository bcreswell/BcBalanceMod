//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.screens.compendium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButtonListener;

public class CardLibSortHeader implements SortHeaderButtonListener {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public CardGroup group;
    public boolean justSorted = false;
    public static final float START_X;
    public static final float SPACE_X;
    private SortHeaderButton rarityButton;
    private SortHeaderButton typeButton;
    private SortHeaderButton costButton;
    private SortHeaderButton nameButton;
    public SortHeaderButton[] buttons;
    public int selectionIndex = -1;
    private static Texture img;
    private Color selectionColor = new Color(1.0F, 0.95F, 0.5F, 0.0F);

    public CardLibSortHeader(CardGroup group) {
        if (img == null) {
            img = ImageMaster.loadImage("images/ui/cardlibrary/selectBox.png");
        }

        this.group = group;
        float xPosition = START_X;
        this.rarityButton = new SortHeaderButton(TEXT[0], xPosition, 0.0F, this);
        xPosition += SPACE_X;
        this.typeButton = new SortHeaderButton(TEXT[1], xPosition, 0.0F, this);
        xPosition += SPACE_X;
        this.costButton = new SortHeaderButton(TEXT[3], xPosition, 0.0F, this);
        if (!Settings.removeAtoZSort) {
            xPosition += SPACE_X;
            this.nameButton = new SortHeaderButton(TEXT[2], xPosition, 0.0F, this);
            this.buttons = new SortHeaderButton[]{this.rarityButton, this.typeButton, this.costButton, this.nameButton};
        } else {
            this.buttons = new SortHeaderButton[]{this.rarityButton, this.typeButton, this.costButton};
        }

    }

    public void setGroup(CardGroup group) {
        this.group = group;
        sortGroupCards();
        SortHeaderButton[] var2 = this.buttons;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SortHeaderButton button = var2[var4];
            //button.reset();
        }

    }

    public void sortGroupCards()
    {
        //default sort first
        group.sortAlphabetically(true);
        group.sortByCost(true);
        group.sortByRarity(true);
        group.sortByType(true);
        group.sortByStatus(true);

        if (nameButton.isActive)
        {
            group.sortAlphabetically(nameButton.isAscending);
        }

        if (costButton.isActive)
        {
            group.sortByCost(costButton.isAscending);
        }

        if (rarityButton.isActive)
        {
            group.sortByRarity(rarityButton.isAscending);
        }

        if (typeButton.isActive)
        {
            group.sortByType(typeButton.isAscending);
        }

        this.justSorted = true;
    }

    public void update() {
        SortHeaderButton[] var1 = this.buttons;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SortHeaderButton button = var1[var3];
            button.update();
        }

    }

    public Hitbox updateControllerInput() {
        SortHeaderButton[] var1 = this.buttons;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SortHeaderButton button = var1[var3];
            if (button.hb.hovered) {
                return button.hb;
            }
        }

        return null;
    }

    public int getHoveredIndex() {
        int retVal = 0;
        SortHeaderButton[] var2 = this.buttons;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SortHeaderButton button = var2[var4];
            if (button.hb.hovered) {
                return retVal;
            }

            ++retVal;
        }

        return 0;
    }

    public void clearActiveButtons() {
        SortHeaderButton[] var1 = this.buttons;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            SortHeaderButton button = var1[var3];
            button.setActive(false);
        }

    }

    public void didChangeOrder(SortHeaderButton button, boolean isAscending) {
        sortGroupCards();
    }

    public void render(SpriteBatch sb) {
        this.updateScrollPositions();
        this.renderButtons(sb);
        this.renderSelection(sb);
    }

    protected void updateScrollPositions() {
        float scrolledY = this.group.getBottomCard().current_y + 230.0F * Settings.yScale;
        SortHeaderButton[] var2 = this.buttons;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SortHeaderButton button = var2[var4];
            button.updateScrollPosition(scrolledY);
        }

    }

    protected void renderButtons(SpriteBatch sb) {
        SortHeaderButton[] var2 = this.buttons;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SortHeaderButton b = var2[var4];
            b.render(sb);
        }

    }

    protected void renderSelection(SpriteBatch sb) {
        for(int i = 0; i < this.buttons.length; ++i) {
            if (i == this.selectionIndex) {
                this.selectionColor.a = 0.7F + MathUtils.cosDeg((float)(System.currentTimeMillis() / 2L % 360L)) / 5.0F;
                sb.setColor(this.selectionColor);
                float doop = 1.0F + (1.0F + MathUtils.cosDeg((float)(System.currentTimeMillis() / 2L % 360L))) / 50.0F;
                sb.draw(img, this.buttons[this.selectionIndex].hb.cX - 80.0F - this.buttons[this.selectionIndex].textWidth / 2.0F * Settings.scale, this.buttons[this.selectionIndex].hb.cY - 43.0F, 100.0F, 43.0F, 160.0F + this.buttons[this.selectionIndex].textWidth, 86.0F, Settings.scale * doop, Settings.scale * doop, 0.0F, 0, 0, 200, 86, false, false);
            }
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("CardLibSortHeader");
        TEXT = uiStrings.TEXT;
        START_X = 430.0F * Settings.xScale;
        SPACE_X = 226.0F * Settings.xScale;
    }
}
