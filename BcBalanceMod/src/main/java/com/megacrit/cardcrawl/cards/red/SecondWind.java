package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SecondWind extends BcSkillCardBase
{
    public static final String ID = "Second Wind";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/second_wind";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
    public int getBlock()
    {
        return !upgraded ? 5 : 7;
    }
    
    @Override
    public String getBaseDescription()
    {
        return  "Exhaust all non-Attack cards. Gain !B! Block for each card Exhausted.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new SecondWindAction(block));
    }
}
