package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import java.util.ArrayList;
import java.util.Iterator;

public class Shovel extends AbstractRelic {
    public static final String ID = "Shovel";

    public Shovel() {
        super("Shovel", "shovel.png", RelicTier.RARE, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public boolean canSpawn() {
        if (AbstractDungeon.floorNum >= 48 && !Settings.isEndless) {
            return false;
        } else {
            int campfireRelicCount = 0;
            Iterator var2 = AbstractDungeon.player.relics.iterator();

            while(true) {
                AbstractRelic r;
                do {
                    if (!var2.hasNext()) {
                        return campfireRelicCount < 2;
                    }

                    r = (AbstractRelic)var2.next();
                } while(!(r instanceof Shovel) && !(r instanceof Girya)); //removed peace pipe since it replaces ritual

                ++campfireRelicCount;
            }
        }
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new DigOption());
    }

    public AbstractRelic makeCopy() {
        return new Shovel();
    }
}
