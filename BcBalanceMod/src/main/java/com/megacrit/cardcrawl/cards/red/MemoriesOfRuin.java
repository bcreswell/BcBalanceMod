package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    public String getImagePath()
    {
        return "red/memoriesOfRuin.png";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
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
        return !upgraded ? 1 : 0;
    }

    public int getVulnerableCount()
    {
        return 1;
    }
    
    public int getHpLoss()
    {
        return 5;
    }
    
    @Override
    public int getMagicNumber() //regen amount
    {
        //total healing:
        //  5 regen -> 15
        //  6 regen -> 21
        //  7 regen -> 28
        //  8 regen -> 36
       
        return 7;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "";
        if (getHpLoss() > 0)
        {
            description += "Sacrifice " + getHpLoss() + " HP. NL ";
        }

        if (getVulnerableCount() > 0)
        {
            description += "Suffer " + getVulnerableCount() + " Vulnerable. NL ";
        }

        description += "Gain !M! Regen.";

        return description;
    }
    
    @Override
    public String getFootnote()
    {
        return " #g" + getTotalHealing() + " healing ";
    }
    
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        if (getHpLoss() > 0)
        {
            addToBot(new LoseHPAction(player, player, getHpLoss()));
        }
        
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, getVulnerableCount(), false)));
        addToBot(new BcApplyPowerAction(new RegenPower(player, magicNumber)));
    }
    
    int getTotalHealing()
    {
        int r = getMagicNumber();
        return (r * (r + 1)) / 2;
    }
}
