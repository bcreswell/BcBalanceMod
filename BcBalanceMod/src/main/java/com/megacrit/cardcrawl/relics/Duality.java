package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

public class Duality extends AbstractRelic
{
    public static final String ID = "Yang";
    
    public Duality()
    {
        super("Yang", "duality.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            //this is way too noisy and annoying slowing down every attack for the whole run
            //addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new BcApplyPowerAction(new DexterityPower(p, 1)));
            addToBot(new BcApplyPowerAction(new LoseDexterityPower(p, 1)));
        }
        
    }
    
    public AbstractRelic makeCopy()
    {
        return new Duality();
    }
}
