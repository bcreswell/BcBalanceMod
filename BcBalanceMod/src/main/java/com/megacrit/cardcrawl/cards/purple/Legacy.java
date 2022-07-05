package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
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
        return !upgraded ? 1 : 0;
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
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever you gain Mantra, Draw a card.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new LegacyPower(player, 1)));
    }
}
