//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class Chaos extends BcSkillCardBase
{
    public static final String ID = "Chaos";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/chaos";
    }
    
    @Override
    public int getChanneledOrbCount()
    {
        if (BcUtility.getCurrentFocus() == 0)
        {
            return getMagicNumber() + 1;
        }
        else
        {
            return getMagicNumber();
        }
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Channel a random Orb. NL If you have zero Focus, channel 1 more.";
        }
        else
        {
            return "Channel !M! random Orbs. NL If you have zero Focus, channel 1 more.";
        }
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    //number of random orbs to Channel
    public int getMagicNumber()
    {
        if (!upgraded)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        int orbsToChannel = getChanneledOrbCount();
        for (int i = 0; i < orbsToChannel; i++)
        {
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
}
