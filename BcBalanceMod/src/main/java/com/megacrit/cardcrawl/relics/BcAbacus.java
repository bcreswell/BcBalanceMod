package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcAbacus extends AbstractRelic
{
    public static final String ID = BcBalanceMod.makeID("BcTheAbacus");
    private static final int BLOCK_AMT = 4;
    private static final int CardDrawPerBlockGain = 10;
    
    public BcAbacus()
    {
        super(ID, "abacus.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription()
    {
        return setDescription(null);
    }
    
    String setDescription(AbstractPlayer.PlayerClass c)
    {
        return "Every #b" + CardDrawPerBlockGain + " cards you draw, NL gain #b" + BLOCK_AMT + " Block.";
    }
    
    public void onEquip()
    {
        counter = 0;
    }
    
    public void onCardDraw(AbstractCard card)
    {
        counter++;
        if (counter >= CardDrawPerBlockGain)
        {
            counter -= CardDrawPerBlockGain;
            
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK_AMT));
        }
    }
    
    public AbstractRelic makeCopy()
    {
        return new BcAbacus();
    }
}
