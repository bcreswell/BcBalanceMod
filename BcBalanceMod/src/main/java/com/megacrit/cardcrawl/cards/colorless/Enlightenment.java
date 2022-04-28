package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.EnlightenmentAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Enlightenment extends BcSkillCardBase
{
    public static final String ID = "Enlightenment";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/enlightenment";
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
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Reduce the cost of all cards in your hand to 1 this turn.";
        }
        else
        {
            return "Reduce the cost of all cards in your hand to 1 this combat.";
        }
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new EnlightenmentAction(this.upgraded));
    }
}
