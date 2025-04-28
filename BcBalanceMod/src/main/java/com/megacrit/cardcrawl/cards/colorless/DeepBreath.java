package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.RetrieveRandomCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeepBreath extends BcSkillCardBase
{
    public static final String ID = "Deep Breath";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/deep_breath";
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
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Retrieve !M! cards at random.";
    }
    
    @Override
    public boolean canBeRetrieved()
    {
        return false;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RetrieveRandomCardsAction(magicNumber));
    }
}