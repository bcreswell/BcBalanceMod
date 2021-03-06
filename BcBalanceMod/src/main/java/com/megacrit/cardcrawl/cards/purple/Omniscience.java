package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Omniscience extends BcSkillCardBase
{
    public static final String ID = "Omniscience";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/omniscience";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return 3;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Choose 1 of your cards. NL Play the chosen card twice and exhaust it.";
    }
    
    @Override
    public String getFootnote()
    {
        return "Can't target Omniscience.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new OmniscienceAction(magicNumber));
    }
}