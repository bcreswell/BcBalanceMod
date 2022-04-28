package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Abacus extends AbstractRelic
{
    public static final String ID = "TheAbacus";
    private static final int BLOCK_AMT = 6;
    
    public Abacus()
    {
        super("TheAbacus", "abacus.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + 6 + DESCRIPTIONS[1];
    }
    
    public void onShuffle()
    {
        //bc: this resolves a bug in the base game where onShuffle is called twice
        // when there's a moment when both draw and discard piles are empty.
        AbstractPlayer player = AbstractDungeon.player;
        int discardSize = player.discardPile.size();
        if (discardSize > 0)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 6));
        }
    }
    
    public AbstractRelic makeCopy()
    {
        return new Abacus();
    }
}
