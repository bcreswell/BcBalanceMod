package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.RemoveNextOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;

public class EquivalencePrinciple extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("EquivalencePrinciple");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Equivalence Principle";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/equivalencePrinciple2.png";
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
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Remove your first Orb without evoking it to gain "+BcUtility.getEnergyString(magicNumber, this)+".";
//        if (upgraded)
//        {
//            description += " NL Create a temporary *Equivalence *Principle.";
//        }
        
        return description;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (BcUtility.getFilledOrbSlotCount() > 0)
        {
            addToBot(new RemoveNextOrbAction());
            addToBot(new GainEnergyAction(magicNumber));
        }
        
//        if (upgraded)
//        {
//            EquivalencePrinciple card = new EquivalencePrinciple();
//            BcUtility.makeCardTemporary(card);
//            BcUtility.addNewCardToHandOrDiscard(card);
//        }
    }
}
