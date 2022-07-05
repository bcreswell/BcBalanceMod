//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import basemod.abstracts.CustomCard;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
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

public class SerpentSkin extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("SerpentSkin");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Serpent Skin";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/serpentSkin.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    //    @Override
//    public int getMagicNumber()
//    {
//        return !upgraded ? 0 : 2;
//    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Shed ALL Weak and Frail.";
        }
        else
        {
            return "Shed ALL Weak and Frail. NL Draw a card.";
        }
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        return player.hasPower(WeakPower.POWER_ID) || player.hasPower(FrailPower.POWER_ID);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int weakAmount = BcUtility.getPowerAmount(WeakPower.POWER_ID);
        int frailAmount = BcUtility.getPowerAmount(FrailPower.POWER_ID);
        int blockToGain = (weakAmount + frailAmount) * magicNumber;
        
        if (weakAmount > 0)
        {
            addToBot(new RemoveSpecificPowerAction(player, player, WeakPower.POWER_ID));
        }
        
        if (frailAmount > 0)
        {
            addToBot(new RemoveSpecificPowerAction(player, player, FrailPower.POWER_ID));
        }

        if (upgraded)
        {
            addToBot(new DrawCardAction(1));
        }
        
//        if (blockToGain > 0)
//        {
//            addToBot(new GainBlockAction(player, player, blockToGain));
//        }
    }
}
