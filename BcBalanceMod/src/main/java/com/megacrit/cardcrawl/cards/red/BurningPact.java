package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurningPact extends BcSkillCardBase
{
    public static final String ID = "Burning Pact";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/burning_pact";
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
        return CardRarity.COMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    public int getHpLoss()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Exhaust a card, NL then draw !M! cards. NL Lose " + getHpLoss() + " HP.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //you don't get the draw if you didn't do the exhaust
        if (player.hand.size() >= 2)
        {
            addToBot(new ExhaustAction(1, false));
            addToBot(new DrawCardAction(player, magicNumber));
        }
        
        addToBot(new LoseHPAction(player, player, getHpLoss()));
    }
}
