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

import java.util.Collections;
import java.util.Iterator;

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
        return !upgraded ? 6 : 12;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Remove all orbs. NL Channel a Dark orb with the mass from all removed Dark orbs + !M!.";
    }
    //endregion
    
    @Override
    public void onInitialized()
    {
        showEvokeValue = true;
    }
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        int totalMass = getTotalDarkOrbMass();
        
        addToTop(new RemoveAllOrbsAction());
        
        if (totalMass > 0)
        {
            AbstractOrb supermassiveDarkOrb = new Dark();
            supermassiveDarkOrb.evokeAmount = totalMass;
            addToBot(new ChannelAction(supermassiveDarkOrb));
            addToBot(new AnimateSpecificOrbAction(supermassiveDarkOrb));
        }
    }
    
    public String getTemporaryExtraDescription()
    {
        int newMass = getTotalDarkOrbMass();
        return "(new dark mass: " + newMass + ")";
    }

//    public void applyPowers()
//    {
//        super.applyPowers();
//
//        int totalMass = getTotalDarkOrbMass();
//
//        rawDescription = getFullDescription() + " NL (total dark mass: " + totalMass + ")";
//        initializeDescription();
//    }
    
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
