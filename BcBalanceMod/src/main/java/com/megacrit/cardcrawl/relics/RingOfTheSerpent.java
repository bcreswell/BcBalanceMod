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

public class RingOfTheSerpent extends AbstractRelic
{
    public static final String ID = "Ring of the Serpent";
    private static final int NUM_CARDS = 2;
    
    public RingOfTheSerpent()
    {
        super("Ring of the Serpent", "serpent_ring.png", RelicTier.BOSS, LandingSound.CLINK);
    }
    
    public String getUpdatedDescription()
    {
        return "Replaces #gRing #gof #gthe #gSnake. NL At the start of your turn, draw #b" + NUM_CARDS + " additional cards.";
    }
    
    public void onEquip()
    {
        //AbstractDungeon.player.masterHandSize += NUM_CARDS;
    }
    
    public void onUnequip()
    {
        //AbstractDungeon.player.masterHandSize -= NUM_CARDS;
    }
    
    public void atTurnStart()
    {
        flash();
        addToBot(new DrawCardAction(NUM_CARDS));
    }
    
    public void atBattleStart()
    {
    }
    
    public boolean canSpawn()
    {
        return AbstractDungeon.player.hasRelic("Ring of the Snake");
    }
    
    public AbstractRelic makeCopy()
    {
        return new RingOfTheSerpent();
    }
}
