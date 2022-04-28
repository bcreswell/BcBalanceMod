//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class RingOfTheSerpent extends AbstractRelic {
    public static final String ID = "Ring of the Serpent";
    private static final int NUM_CARDS = 1;

    public RingOfTheSerpent() {
        super("Ring of the Serpent", "serpent_ring.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            return "Replaces #gRing #gof #gthe #gSnake. NL Turn 1: draw #b2 extra cards. NL Turn 2 and on: draw #b1 extra card.";
        }
        else
        {
            return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
        }
    }

    public void onEquip() {
        ++AbstractDungeon.player.masterHandSize;
    }

    public void onUnequip() {
        --AbstractDungeon.player.masterHandSize;
    }

    public void atTurnStart() {
        this.flash();
    }

    //added this ring of the snake logic
    public void atBattleStart()
    {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("Ring of the Snake");
    }

    public AbstractRelic makeCopy() {
        return new RingOfTheSerpent();
    }
}
