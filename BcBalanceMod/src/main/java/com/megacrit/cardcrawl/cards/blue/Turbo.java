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
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
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
    public String getId()
    {
        return ID;
    }
    
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
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    public int getCardDraw()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain "+getEnergyString(magicNumber)+". NL Draw "+getCardCountString(getCardDraw())+". NL NL Shuffle a *Void into your Draw Pile.";
        //return "Gain "+getEnergyString(magicNumber)+". NL Draw "+getCardCountString(getCardDraw())+". NL NL Add a *Void into your discard pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(getCardDraw()));
        
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true, false));
        //addToBot(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }
}