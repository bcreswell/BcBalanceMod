//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import java.util.Comparator;

public class MasterDeckSortHeader extends CardLibSortHeader {
    private static final int BAR_W = 1334;
    private static final int BAR_H = 102;
    private static final Color BAR_COLOR = new Color(0.4F, 0.4F, 0.4F, 1.0F);
    private static final Color IRONCLAD_COLOR = new Color(0.5F, 0.1F, 0.1F, 1.0F);
    private static final Color SILENT_COLOR = new Color(0.25F, 0.55F, 0.0F, 1.0F);
    private static final Color DEFECT_COLOR = new Color(0.01F, 0.34F, 0.52F, 1.0F);
    private static final Comparator<AbstractCard> BY_TYPE = (a, b) -> {
        return (a.type.name() + a.name).compareTo(b.type.name() + b.name);
    };
    private static final Comparator<AbstractCard> ALPHA = (a, b) -> {
        return a.name.compareTo(b.name);
    };
    private static final Comparator<AbstractCard> BY_COST = (a, b) -> {
        return ("" + a.cost + a.name).compareTo("" + b.cost + b.name);
    };
    private static final Comparator<AbstractCard> PURE_REVERSE = (a, b) -> {
        return a.cardID.equals(b.cardID) ? 0 : -1;
    };
    private MasterDeckViewScreen masterDeckView;
    private float scrollY;

    public MasterDeckSortHeader(MasterDeckViewScreen masterDeckView) {
        super((CardGroup)null);
        this.masterDeckView = masterDeckView;
        this.buttons[0] = new SortHeaderButton(TEXT[5], START_X, 0.0F, this);
        this.buttons[0].setActive(true);
        float HB_W = this.buttons[0].hb.width;
        float leftSideOffset = (float)Settings.WIDTH / 2.0F - HB_W * (float)this.buttons.length / 2.0F;

        for(int i = 0; i < this.buttons.length; ++i) {
            SortHeaderButton button = this.buttons[i];
            button.hb.move(leftSideOffset + HB_W * (float)i + HB_W / 2.0F, button.hb.cY);
        }

    }

    @Override
    public void sortGroupCards()
    {
        //default order - order added to deck
        if ((buttons.length > 0) && buttons[0].isActive)
        {
            masterDeckView.setSortOrder(buttons[0].isAscending ? (Comparator)null : PURE_REVERSE);
        }

        if ((buttons.length > 3) && buttons[3].isActive)
        {
            masterDeckView.setSortOrder(buttons[3].isAscending ? ALPHA : ALPHA.reversed());
        }

        if ((buttons.length > 2) && buttons[2].isActive)
        {
            masterDeckView.setSortOrder(buttons[2].isAscending ? BY_COST : BY_COST.reversed());
        }

        if ((buttons.length > 1) && buttons[1].isActive)
        {
            masterDeckView.setSortOrder(buttons[1].isAscending ? BY_TYPE : BY_TYPE.reversed());
        }

        this.justSorted = true;
    }

    public void didChangeOrder(SortHeaderButton button, boolean isAscending) {
        sortGroupCards();
    }

    protected void updateScrollPositions() {
    }

    public void render(SpriteBatch sb) {
        switch (AbstractDungeon.player.chosenClass) {
            case IRONCLAD:
                sb.setColor(IRONCLAD_COLOR);
                break;
            case THE_SILENT:
                sb.setColor(SILENT_COLOR);
                break;
            case DEFECT:
                sb.setColor(DEFECT_COLOR);
                break;
            default:
                sb.setColor(BAR_COLOR);
        }

        sb.draw(ImageMaster.COLOR_TAB_BAR, (float)Settings.WIDTH / 2.0F - 667.0F, this.scrollY - 51.0F, 667.0F, 51.0F, 1334.0F, 102.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1334, 102, false, false);
        super.render(sb);
    }

    public void updateScrollPosition(float y) {
        this.scrollY = y + 240.0F * Settings.scale;
        SortHeaderButton[] var2 = this.buttons;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SortHeaderButton button = var2[var4];
            button.updateScrollPosition(this.scrollY);
        }

    }
}
