package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Bloodletting extends BcSkillCardBase
{
    public static final String ID = "Bloodletting";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/bloodletting";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
    public boolean getEthereal()
    {
        return true;
    }
    
    public int getHealthLost()
    {
        return 3;
    }
    
    public int getEnergyGained()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Sacrifice " + getHealthLost() + " HP. NL Gain [R] [R]. NL Conserve any leftover energy for next turn.";
        }
        else
        {
            return "Sacrifice " + getHealthLost() + " HP. NL Gain [R] [R] [R]. NL Conserve any leftover energy for next turn.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new OfferingEffect(), 0.1F));
        addToBot(new LoseHPAction(player, player, getHealthLost()));
        addToBot(new GainEnergyAction(getEnergyGained()));
        addToBot(new BcApplyPowerAction(new ConserveEnergyPower(player, 1)));
    }
}
