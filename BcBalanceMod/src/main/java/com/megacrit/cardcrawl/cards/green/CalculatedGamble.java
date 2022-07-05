package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CalculatedGambleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalculatedGamble extends BcSkillCardBase
{
    public static final String ID = "Calculated Gamble";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/calculated_gamble";
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Choose any number of cards to discard, NL then draw that many cards.";
        }
        else
        {
            return "Choose any number of cards to discard, NL then draw that many cards + 1.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new CalculatedGambleAction(true));
        if (upgraded)
        {
            addToBot(new DrawCardAction(1));
        }
    }
}
