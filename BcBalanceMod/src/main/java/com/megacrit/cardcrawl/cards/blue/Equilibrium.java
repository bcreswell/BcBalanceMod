//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Equilibrium extends BcSkillCardBase
{
    public static final String ID = "Undo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/equilibrium";
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
        return CardRarity.COMMON;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 6 : 8;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 12 : 16;
    }
    
    @Override
    public String getBaseDescription()
    {
        int zeroB = BcUtility.getModifiedBlock(getMagicNumber());
        int b = BcUtility.getModifiedBlock(getBlock());
        String zeroFocusBlock = null;
        String otherwiseBlock = null;
        
        if (BcUtility.isPlayerInCombat())
        {
            if (BcUtility.getCurrentFocus() == 0)
            {
                zeroFocusBlock = "Zero Focus: " + BcUtility.getModifiedValueString(getMagicNumber(), zeroB) + " Block.";
                otherwiseBlock = "#aOtherwise: #a" + b + " #aBlock.";
            }
            else
            {
                zeroFocusBlock = "#aZero #aFocus: #a" + zeroB + " #aBlock.";
                otherwiseBlock = "Otherwise: " + BcUtility.getModifiedValueString(getBlock(), b) + " Block.";
            }
        }
        else
        {
            zeroFocusBlock = "Zero Focus: " + zeroB + " Block.";
            otherwiseBlock = "Otherwise: " + b + " Block.";
        }
        
        return "Retain your hand and Block this turn. NL Gain some Block. NL " + zeroFocusBlock + " NL " + otherwiseBlock;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new BcApplyPowerAction(new EquilibriumPower(player, 1)));
        addToBot(new BcApplyPowerAction(new BlurPower(player, 1)));
    }
    
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void applyPowers()
    {
        baseBlock = getBlock();
        if (BcUtility.getCurrentFocus() == 0)
        {
            baseBlock = getMagicNumber();
        }
        
        super.applyPowers();
    }
}
