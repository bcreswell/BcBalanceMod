package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
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
    public String getBaseDescription()
    {
        if (upgraded)
        {
            return "Choose 1 of 4 cards of your color to create. NL It costs 0 this turn.";
        }
        else
        {
            return "Choose 1 of 3 cards of your color to create. NL It costs 0 this turn.";
        }
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new DiscoveryImprovedAction(upgraded?4:3,true,false,null,null));
    }
}
