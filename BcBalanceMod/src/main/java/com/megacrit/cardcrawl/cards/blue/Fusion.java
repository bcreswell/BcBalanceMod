package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Plasma;

public class Fusion extends BcSkillCardBase
{
    public static final String ID = "Fusion";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/fusion";
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
        return !upgraded ? 2 : 1;
    }

    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return getMagicNumber();
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Channel !M! Plasma.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(new ChannelAction(new Plasma()));
        }        
    }
}
