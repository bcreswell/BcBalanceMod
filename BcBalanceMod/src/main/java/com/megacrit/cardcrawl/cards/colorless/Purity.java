//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Purity extends BcSkillCardBase
{
    public static final String ID = "Purity";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/purity";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
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
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }

    public int getCardDraw()
    {
        return 0;
    }

    @Override
    public String getBaseDescription()
    {
        return "Exhaust up to !M! cards in your hand and then draw a card for each.";
//        int cardDraw = getCardDraw();
//        if (cardDraw >= 1)
//        {
//            return "Draw "+ BcUtility.getCardCountString(cardDraw) +". NL Exhaust up to !M! cards in your hand.";
//        }
//        else
//        {
//            return "Exhaust up to !M! cards in your hand.";
//        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int cardDraw = getCardDraw();
        if (cardDraw > 0)
        {
            addToBot(new DrawCardAction(cardDraw));
        }
        addToBot(new PurityAction(magicNumber, false, true, true));
    }
}
