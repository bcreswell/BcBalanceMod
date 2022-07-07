package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class Legacy extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("Legacy");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Legacy";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/legacy.png";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Whenever you gain Mantra, Draw a card.";
        }
        else
        {
            return "Whenever you gain !M! or more Mantra, NL Draw a card.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new LegacyPower(player, 1, magicNumber)));
    }
}
