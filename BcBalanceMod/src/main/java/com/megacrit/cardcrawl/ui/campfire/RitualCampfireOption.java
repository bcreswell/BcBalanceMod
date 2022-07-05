package com.megacrit.cardcrawl.ui.campfire;

import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.campfire.*;

public class RitualCampfireOption extends AbstractCampfireOption
{
    static Texture ritualTexture;
    
    static
    {
        ritualTexture = ImageMaster.loadImage("images/ui/campfire/ritual.png");
    }
    
    public RitualCampfireOption(boolean active)
    {
        label = "Ritual";
        usable = active;
        description = "Sacrifice " + BcUtility.getCurseRemovalSacrificeAmount() + " HP, Remove a Curse, Random Upgrade.";
        img = ritualTexture;
    }
    
    public void useOption()
    {
        if (usable)
        {
            //bc: had to put the HP loss here. it would crash if it was inside the ritual effect.
            CardCrawlGame.sound.play("BLOOD_SPLAT");
            AbstractDungeon.player.damage(new DamageInfo(null, BcUtility.getCurseRemovalSacrificeAmount(), DamageInfo.DamageType.HP_LOSS));
            
            AbstractDungeon.effectList.add(new CampfireRitualEffect());
        }
    }
    
    @Override
    public void update()
    {
        super.update();
    }
}
