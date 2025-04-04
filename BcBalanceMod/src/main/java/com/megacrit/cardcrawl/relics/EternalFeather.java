package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;

public class EternalFeather extends AbstractRelic {
    public static final String ID = "Eternal Feather";

    public EternalFeather() {
        super("Eternal Feather", "eternal_feather.png", RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return "For every 5 cards in your deck, heal 2 HP whenever you enter a Rest Site.";
    }

    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof RestRoom) {
            this.flash();
            int amountToGain = AbstractDungeon.player.masterDeck.size() / 5 * 2;
            AbstractDungeon.player.heal(amountToGain);
        }
    }

    public AbstractRelic makeCopy() {
        return new EternalFeather();
    }
}
