package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Seek extends BcSkillCardBase
{
    public static final String ID = "Seek";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/seek";
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
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
//    @Override
//    public boolean canBeRetrieved()
//    {
//        return upgraded;
//    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Pick "+ getCardCountString(magicNumber) +" to Draw.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawOfPlayersChoiceAction(magicNumber, false, false, false, false));
    }
}
