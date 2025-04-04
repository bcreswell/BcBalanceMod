package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MagnetismPower;

public class Magnetism extends BcPowerCardBase
{
    public static final String ID = "Magnetism";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/power/magnetism";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Magnetism";
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }

    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Start of turn: NL Retrieve a card at random.";
        }
        else
        {
            return "Start of turn: NL Retrieve " + BcUtility.getCardCountString(magicNumber) + " randomly.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new MagnetismPower(player, magicNumber)));
    }
}
