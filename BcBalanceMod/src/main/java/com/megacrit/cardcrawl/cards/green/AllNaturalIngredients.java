package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class AllNaturalIngredients extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("AllNaturalIngredients");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "All Natural Ingredients";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/allNaturalIngredients.png";
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
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When you Inflict Weak on an enemy, also Inflict !M! Poison.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new AllNaturalIngredientsPower(player, magicNumber)));
    }
}
