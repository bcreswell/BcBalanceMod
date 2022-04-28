package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class MemoriesOfRuin extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("MemoriesOfRuin");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Memories of Ruin";
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Lose " + getHpLoss() + " HP. NL Suffer " + getVulnerableCount() + " Vulnerable. NL Gain !M! Regen. NL (" + getTotalHealing() + " healing total)";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/memoriesOfRuin.png";
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
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    public int getVulnerableCount()
    {
        return 1;
    }
    
    public int getHpLoss()
    {
        //21 - 8 = 13 profit healing
        return 8;
    }
    
    @Override
    public int getMagicNumber() //regen amount
    {
        //total healing:
        //  5 regen -> 15
        //  6 regen -> 21
        //  7 regen -> 28
        //  8 regen -> 36
        
        return 6;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        this.addToBot(new LoseHPAction(player, player, getHpLoss()));
        this.addToBot(new ApplyPowerAction(player, player, new VulnerablePower(player, getVulnerableCount(), false), getVulnerableCount()));
        this.addToBot(new ApplyPowerAction(player, player, new RegenPower(player, magicNumber), magicNumber));
    }
    
    int getTotalHealing()
    {
        int r = getMagicNumber();
        return (r * (r + 1)) / 2;
    }
}
