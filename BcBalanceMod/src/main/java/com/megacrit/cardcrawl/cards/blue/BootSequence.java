package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BootSequence extends BcSkillCardBase
{
    public static final String ID = "BootSequence";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Boot Sequence";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/boot_sequence";
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 10 : 12;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Gain !B! Block.";
        }
        else
        {
            return "Gain !B! Block. NL Draw a card.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        
        if (upgraded)
        {
            addToBot(new DrawCardAction(1));
        }
    }
}
