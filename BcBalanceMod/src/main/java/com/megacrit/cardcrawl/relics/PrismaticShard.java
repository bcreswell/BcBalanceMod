package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PrismaticShard extends AbstractRelic {
    public static final String ID = "PrismaticShard";
    
    public PrismaticShard() {
        super("PrismaticShard", "prism.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription() {
        return "Combat reward screens now contain Colorless and Foreign cards.";
    }
    
    public AbstractRelic makeCopy() {
        return new PrismaticShard();
    }
    
    public void onEquip() {
        if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT && AbstractDungeon.player.masterMaxOrbs == 0) {
            AbstractDungeon.player.masterMaxOrbs = 1;
        }
        
    }
}
