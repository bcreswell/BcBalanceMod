package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;

public class Darkness extends BcSkillCardBase
{
    public static final String ID = "Darkness";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/darkness";
    }
    
    @Override
    public int getChanneledOrbCount()
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
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Channel !M! Dark.";
        }
        else
        {
            return "Channel !M! Dark. NL Trigger the passive ability of all Dark orbs.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ChannelAction(new Dark()));
        
        if (upgraded)
        {
            addToBot(new BcDarkImpulseAction());
        }
    }
}
