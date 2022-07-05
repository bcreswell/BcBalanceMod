//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javax.smartcardio.Card;

public class Turbo extends BcSkillCardBase
{
    public static final String ID = "Turbo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/turbo";
    }
    
    @Override
    public void onInitialized()
    {
        cardsToPreview = new VoidCard();
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Turbo";
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Gain [B] [B]. NL Draw 1 card. NL Add a *Void into your discard pile.";
        }
        else
        {
            return "Gain [B] [B] [B]. NL Draw 1 card. NL Add a *Void into your discard pile.";
        }
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(1));
        addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }
}