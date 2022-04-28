package com.megacrit.cardcrawl.relics;

public class RunicPyramid extends AbstractRelic {
    public static final String ID = "Runic Pyramid";
    
    public RunicPyramid() {
        super("Runic Pyramid", "runicPyramid.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
        return "At the end of your turn, you only discard unplayable status and curse cards.";
    }
    
    public AbstractRelic makeCopy() {
        return new RunicPyramid();
    }
}
