package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class Retaliate extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Retaliate");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Retaliate";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/retaliate.png";
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
    public int getMagicNumber()
    {
        return !upgraded ? 20 : 30;
    }
    
    @Override
    public String getBaseDescription()
    {
        int str = BcUtility.getCurrentStrength();
        
        String retaliateDmgString = "" + (getMagicNumber() + str);
        
        if (str > 0)
        {
            retaliateDmgString = "#g" + retaliateDmgString;
        }
        else if (str < 0)
        {
            retaliateDmgString = "#r" + retaliateDmgString;
        }
        
        return "Until next turn, NL Retaliate for " + retaliateDmgString + " damage the first time each enemy attacks. NL (modified by Strength)";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int retaliateDmg = magicNumber + BcUtility.getCurrentStrength();
        addToBot(new BcApplyPowerAction(new RetaliatePower(player, retaliateDmg)));
    }
}
