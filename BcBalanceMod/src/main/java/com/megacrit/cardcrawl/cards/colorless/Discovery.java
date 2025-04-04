package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.DiscoveryImprovedAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Discovery extends BcSkillCardBase
{
    public static final String ID = "Discovery";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/discovery";
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
    public String getDisplayName()
    {
        return "Discovery";
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Choose 1 of !M! cards of any color to create. NL It costs 0 and is temporary.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DiscoveryImprovedAction(magicNumber, true, false, null, null, true));
    }
}
