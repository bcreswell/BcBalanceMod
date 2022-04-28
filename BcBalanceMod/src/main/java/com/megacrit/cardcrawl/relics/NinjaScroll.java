package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

public class NinjaScroll extends AbstractRelic
{
    public static final String ID = "Ninja Scroll";
    private static final int AMOUNT = 3;
    
    public NinjaScroll()
    {
        super("Ninja Scroll", "ninjaScroll.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "Start each combat with " + AMOUNT + " Hidden Shivs.";
    }
    
    public void atBattleStartPreDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        addToBot(new RelicAboveCreatureAction(player, this));
        addToBot(new ApplyPowerAction(player, player, new HiddenShivPower(player, AMOUNT), AMOUNT));
    }
    
    public AbstractRelic makeCopy()
    {
        return new NinjaScroll();
    }
}