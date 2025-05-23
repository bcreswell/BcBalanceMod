package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.EscapePlanAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.*;

public class EscapePlan extends BcSkillCardBase
{
    public static final String ID = "Escape Plan";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/escapePlan.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1 ;
    }
    
    @Override
    public String getId()
    {
        return ID;
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Create one: NL - *Panic *Button+, NL - *Dark *Shackles+ or NL - *Force *of *Will+.";
//        if (!upgraded)
//        {
//            return "Create one: NL - *Panic *Button, NL - *Dark *Shackles, or NL - *Force *of *Will.";
//        }
//        else
//        {
//            return "Create one: NL - *Panic *Button+, NL - *Dark *Shackles+ or NL - *Force *of *Will+.";
//        }
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        ArrayList<AbstractCard> choices = new ArrayList();
        choices.add(new PanicButton());
        choices.add(new DarkShackles());
        choices.add(new ForceOfWill());
        
        for (AbstractCard card : choices)
        {
            card.upgrade();
        }
//        if (upgraded)
//        {
//            for (AbstractCard card : choices)
//            {
//                card.upgrade();
//            }
//        }
        
        addToBot(new ChooseOneCard(choices));
    }
}
