package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class Outnumbered extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("Outnumbered");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Outnumbered";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/outnumbered.png";
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
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When you play an Attack, temporarily reduce the target's Strength by !M! x [G] spent.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new OutnumberedPower(player, magicNumber)));
    }
}
