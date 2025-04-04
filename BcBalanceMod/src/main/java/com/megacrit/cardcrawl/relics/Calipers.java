//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class Calipers extends AbstractRelic {
    public static final String ID = "Calipers";
    public static final int BLOCK_LOSS = 10;

    public Calipers() {
        super("Calipers", "calipers.png", RelicTier.RARE, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BLOCK_LOSS + this.DESCRIPTIONS[1];
    }

    public AbstractRelic makeCopy() {
        return new Calipers();
    }
}
