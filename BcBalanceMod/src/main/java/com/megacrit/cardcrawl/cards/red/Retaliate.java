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
        return CardRarity.UNCOMMON;
    }
    
    
    public int getRetaliateDamage()
    {
        int dmg = !upgraded ? 18 : 24;
        
        if (BcUtility.isPlayerInCombat())
        {
            return dmg + BcUtility.getCurrentStrength();
        }
        
        return dmg;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Until next turn, NL Retaliate for " + getRetaliateDamage() + " damage the first time each enemy attacks. NL (increased by strength)";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new RetaliatePower(player, getRetaliateDamage())));
    }
}
