package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

public class Glacier extends BcSkillCardBase
{
    public static final String ID = "Glacier";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/glacier";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return getMagicNumber();
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 4 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Channel !M! Frost.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(new ChannelAction(new Frost()));
        }        
    }
}
