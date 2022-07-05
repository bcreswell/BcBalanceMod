package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.relics.*;

public class Abomination extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Abomination");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Abomination";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/abomination.png";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public CardTarget getCardTarget()
    {
        return CardTarget.ALL_ENEMY;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 9 : 13;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Random enemies are struck for !M! damage for each time you've played an Ethereal card this combat.";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        if (BcBalancingScales.EtherealPlayedCount == 1)
        {
            return "" + BcBalancingScales.EtherealPlayedCount + " ethereal card";
        }
        else
        {
            return "" + BcBalancingScales.EtherealPlayedCount + " ethereal cards";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < BcBalancingScales.EtherealPlayedCount; i++)
        {
            addToBot(new AbominationAction(this));
        }
    }
}
