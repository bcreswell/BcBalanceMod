package com.megacrit.cardcrawl.ui.campfire;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;

public class TokeOption extends AbstractCampfireOption
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public TokeOption(boolean active)
    {
        this.label = TEXT[0];
        this.usable = active;
        this.description = "Select a Card to Remove. Upgrade a random card."; // TEXT[1];
        this.img = ImageMaster.CAMPFIRE_TOKE_BUTTON;
    }
    
    public void useOption()
    {
        if (this.usable)
        {
            AbstractDungeon.effectList.add(new CampfireTokeEffect());
        }
        
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("Toke Option");
        TEXT = uiStrings.TEXT;
    }
}
