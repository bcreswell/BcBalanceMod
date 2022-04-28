//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import java.util.Iterator;

public class SacredBark extends AbstractRelic {
    public static final String ID = "SacredBark";

    public SacredBark() {
        super("SacredBark", "bark.png", RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            return "Double the effectiveness of potions. NL Gain an extra Potion Slot.";
        }
        else
        {
            return this.DESCRIPTIONS[0];
        }
    }

    public void onEquip() {
        Iterator var1 = AbstractDungeon.player.potions.iterator();

        while(var1.hasNext()) {
            AbstractPotion p = (AbstractPotion)var1.next();
            p.initializeData();
        }

        //add 1 potion slot
        AbstractDungeon.player.potionSlots += 1;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }

    public AbstractRelic makeCopy() {
        return new SacredBark();
    }
}
