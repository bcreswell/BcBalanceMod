package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.LiftOption;
import java.util.ArrayList;
import java.util.Iterator;

public class Girya extends AbstractRelic {
    public static final String ID = "Girya";
    public static final int STR_LIMIT = 3;

    public Girya() {
        super("Girya", "kettlebell.png", RelicTier.RARE, LandingSound.HEAVY);
        this.counter = 0;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        if (this.counter != 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

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
        options.add(new LiftOption(this.counter < 3));
    }

    public AbstractRelic makeCopy() {
        return new Girya();
    }
}
