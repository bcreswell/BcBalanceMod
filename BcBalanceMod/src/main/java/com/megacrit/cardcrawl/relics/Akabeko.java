package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class Akabeko extends AbstractRelic {
    public static final String ID = "Akabeko";
    private static final int VIGOR = 8;
    
    public Akabeko() {
        super("Akabeko", "akabeko.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 8 + this.DESCRIPTIONS[1];
    }
    
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 8), 8));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    public AbstractRelic makeCopy() {
        return new Akabeko();
    }
}
