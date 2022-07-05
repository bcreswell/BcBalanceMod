package com.megacrit.cardcrawl.relics;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcSundial extends AbstractRelic
{
    public static final String ID = BcBalanceMod.makeID("BcSundial");
    private static final int ENERGY_AMT = 1;
    private static final int CardDrawPerEnergyGain = 24;
    
    public BcSundial()
    {
        super(ID, "sundial.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription()
    {
        return AbstractDungeon.player != null ? setDescription(AbstractDungeon.player.chosenClass) : setDescription(null);
    }
    
    private String setDescription(AbstractPlayer.PlayerClass c)
    {
        return "Every #b" + CardDrawPerEnergyGain + " cards you draw, NL gain [E] .";
    }
    
    public void onEquip()
    {
        counter = 0;
    }
    
    public void onCardDraw(AbstractCard card)
    {
        counter++;
        if (counter >= CardDrawPerEnergyGain)
        {
            counter -= CardDrawPerEnergyGain;
            
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(ENERGY_AMT));
        }
    }
    
    public AbstractRelic makeCopy()
    {
        return new BcSundial();
    }
}
