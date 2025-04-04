package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ParallelProcessing extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("ParallelProcessing");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Parallel Processing";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/parallelProcessing.png";
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
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 4 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block for each of your Orbs.";
    }
    //endregion
    
    String getFinalBlockString()
    {
        int x = BcUtility.getFilledOrbSlotCount();
        int unmoddifiedFinalBlock = baseBlock * x;
        int finalBlock = block * x;
        
        return BcUtility.getModifiedValueString(unmoddifiedFinalBlock, finalBlock);
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        return getFinalBlockString() + " block";
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int filledOrbCount = BcUtility.getFilledOrbSlotCount();
        for (int i = 0; i < filledOrbCount; i++)
        {
            addToBot(new GainBlockAction(player, player, block));
        }
    }
}
