package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class WaxOnWaxOff extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("WaxOnWaxOff");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Wax On. Wax Off.";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/waxOnWaxOff.png";
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Each turn: NL The first time you Discard, NL Draw a card.";
        }
        else
        {
            return "Each turn: NL The first !M! times you Discard, NL Draw a card.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new WaxOnPower(player, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(player, player, new WaxOffPower(player, magicNumber), magicNumber));
    }
}
