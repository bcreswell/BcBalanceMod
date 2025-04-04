//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class Chaos extends BcSkillCardBase
{
    public static final String ID = "Chaos";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/chaos";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    //number of orbs to Channel
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    public int getExtraOrbsToChannel()
    {
        return 1;
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        int orbsToHardChannel = getMagicNumber();
        int emptySlotCount = BcUtility.getEmptyOrbSlotCount();
        emptySlotCount = Math.max(0, emptySlotCount - orbsToHardChannel);

        return orbsToHardChannel + Math.min(emptySlotCount, getExtraOrbsToChannel());
    }
    
    @Override
    public String getBaseDescription()
    {
        String description;
        if (magicNumber == 1)
        {
            description = "Channel a random Orb.";
        }
        else
        {
            description = "Channel !M! random Orbs.";
        }
        
        int extraOrbs = getExtraOrbsToChannel();
        if (extraOrbs == 1)
        {
            description += " NL NL If an empty Orb Slot remains, Channel 1 more.";
        }
        else if (extraOrbs >= 2)
        {
            description += " NL NL If empty Orb Slot(s) remain, Channel up to " + extraOrbs + " more.";
        }
        
        return description;
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        //glow when you'd channel the max orb count
        return (getOrbCountToChannel() == magicNumber + getExtraOrbsToChannel());
    }
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        int orbsToChannel = getOrbCountToChannel();
        for (int i = 0; i < orbsToChannel; i++)
        {
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true), true));
        }
    }
}