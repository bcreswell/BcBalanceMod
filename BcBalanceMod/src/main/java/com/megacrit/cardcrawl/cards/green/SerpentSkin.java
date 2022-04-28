//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import com.megacrit.cardcrawl.actions.common.ChooseOneCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.cards.colorless.Panacea;
import com.megacrit.cardcrawl.cards.colorless.Purity;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;
import java.util.Iterator;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class SerpentSkin extends CustomCard
{
    public static final String ID = BcBalanceMod.makeID("SerpentSkin");
    private static final CardStrings cardStrings;
    
    public SerpentSkin()
    {
        super(
                ID,
                cardStrings.NAME,
                makeCardPath("green/serpentSkin.png"),
                1,
                cardStrings.DESCRIPTION,
                CardType.SKILL,
                CardColor.GREEN,
                CardRarity.UNCOMMON,
                CardTarget.SELF);
        
        this.selfRetain = true;
        this.exhaust = true;
    }
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractPower weak = AbstractDungeon.player.getPower(WeakPower.POWER_ID);
        if (weak != null)
        {
            this.addToBot(new RemoveSpecificPowerAction(p, p, WeakPower.POWER_ID));
        }
        
        AbstractPower frail = AbstractDungeon.player.getPower(FrailPower.POWER_ID);
        if (frail != null)
        {
            this.addToBot(new RemoveSpecificPowerAction(p, p, FrailPower.POWER_ID));
        }
    }
    
    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        
        AbstractPower weak = AbstractDungeon.player.getPower(WeakPower.POWER_ID);
        AbstractPower frail = AbstractDungeon.player.getPower(FrailPower.POWER_ID);
        
        if ((weak != null) || (frail != null))
        {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static
    {
        cardStrings = new CardStrings();
        cardStrings.NAME = " Serpent Skin";
        cardStrings.DESCRIPTION = "Retain. NL Shed ALL NL Weak and Frail. NL Exhaust.";
        cardStrings.UPGRADE_DESCRIPTION = "Retain. NL Shed ALL NL Weak and Frail. NL Exhaust.";
    }
}
