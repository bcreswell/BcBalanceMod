package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.DeepDarknessPower;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class Supermassive extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Supermassive");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Supermassive";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/supermassive.png";
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
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 8;
    }
    
    @Override
    public int getChanneledOrbCount()
    {
        int newMass = getTotalDarkOrbMass();
        if (newMass > getMagicNumber())
        {
            //there was already at least 1 dark orb present.
            //this means the mass will be added to it and no new orb will be channeled.
            return 0;
        }
        else
        {
            return 1;
        }
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Collapse all Dark orbs into a supermassive one with their combined mass + !M!.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        int totalMass = getTotalDarkOrbMass();
        
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        AbstractOrb supermassiveDarkOrb = null;
        int removedOrbCount = 0;
        for (int i = 0; i < orbs.size(); i++)
        {
            AbstractOrb currentOrb = orbs.get(i);
            if (currentOrb instanceof Dark)
            {
                if (supermassiveDarkOrb == null)
                {
                    supermassiveDarkOrb = currentOrb;
                    supermassiveDarkOrb.evokeAmount = totalMass;
                    supermassiveDarkOrb.updateDescription();
                }
                else //remove it
                {
                    removedOrbCount++;
                    removeSpecificOrbAndShiftOthersForward(i);
                    i--;
                }
            }
        }
        
        if (supermassiveDarkOrb == null)
        {
            supermassiveDarkOrb = new Dark();
            supermassiveDarkOrb.evokeAmount = totalMass;
            supermassiveDarkOrb.updateDescription();
            addToBot(new ChannelAction(supermassiveDarkOrb));
        }
        else
        {
            for (int i = 0; i < removedOrbCount + 1; i++)
            {
                addToBot(new TrueWaitAction(.1f));
                addToBot(new AnimateSpecificOrbAction(supermassiveDarkOrb));
            }
        }
    }
    
    void removeSpecificOrbAndShiftOthersForward(int orbIndex)
    {
        AbstractOrb currentOrb = getOrb(orbIndex);
        AbstractOrb nextOrb = getOrb(orbIndex + 1);
        
        if (currentOrb == null)
        {
            return;
        }
        
        if (nextOrb == null)
        {
            setOrb(orbIndex, null);
        }
        else
        {
            setOrb(orbIndex, nextOrb);
            removeSpecificOrbAndShiftOthersForward(orbIndex + 1);
        }
    }
    
    AbstractOrb getOrb(int orbIndex)
    {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        if (orbIndex < orbs.size())
        {
            AbstractOrb orb = orbs.get(orbIndex);
            if (!(orb instanceof EmptyOrbSlot))
            {
                return orb;
            }
        }
        
        return null;
    }
    
    void setOrb(int orbIndex, AbstractOrb newOrb)
    {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        
        if (orbIndex < orbs.size())
        {
            AbstractOrb currentOrb = orbs.get(orbIndex);
            
            if (newOrb == null)
            {
                newOrb = new EmptyOrbSlot(0, 0);
            }
            
            newOrb.setSlot(orbIndex, AbstractDungeon.player.maxOrbs);
            orbs.set(orbIndex, newOrb);
        }
    }
    
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int newMass = getTotalDarkOrbMass();
        return "new dark mass: " + newMass;
    }
    
    int getTotalDarkOrbMass()
    {
        int totalMass = magicNumber;
        for (AbstractOrb orb : AbstractDungeon.player.orbs)
        {
            if (orb instanceof Dark)
            {
                Dark darkOrb = (Dark) orb;
                totalMass += darkOrb.evokeAmount;
            }
        }
        
        return totalMass;
    }
}
