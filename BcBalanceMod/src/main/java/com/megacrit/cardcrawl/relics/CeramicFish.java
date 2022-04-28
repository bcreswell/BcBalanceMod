package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CeramicFish extends AbstractRelic {
    public static final String ID = "CeramicFish";
    private static final int GOLD_AMT = 9;
    
    public CeramicFish() {
        super("CeramicFish", "ceramic_fish.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 9 + this.DESCRIPTIONS[1];
    }
    
    public void use() {
        this.flash();
        --this.counter;
        if (this.counter == 0) {
            this.setCounter(0);
        } else {
            this.description = this.DESCRIPTIONS[1];
        }
        
    }
    
    public void onObtainCard(AbstractCard c) {
        AbstractDungeon.player.gainGold(9);
    }
    
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 24;
    }
    
    public AbstractRelic makeCopy() {
        return new CeramicFish();
    }
}
