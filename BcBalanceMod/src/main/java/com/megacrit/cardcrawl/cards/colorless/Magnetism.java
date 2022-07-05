package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
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
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Magnetism";
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean isARetrieveCard()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Start of turn: NL Retrieve a card randomly.";
        }
        else
        {
            return "Start of turn: NL Retrieve !M! cards randomly.";
        }
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new MagnetismPower(player, magicNumber)));
    }
}
