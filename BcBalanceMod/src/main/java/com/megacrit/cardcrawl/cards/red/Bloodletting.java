package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

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
    
    public int getHealthLost()
    {
        return 3;
    }
    
    public int getEnergyGained()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Lose " + getHealthLost() + " HP. NL Gain [R] [R].";
        }
        else
        {
            return "Lose " + getHealthLost() + " HP. NL Gain [R] [R]. NL Retain your leftover energy until next turn.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new LoseHPAction(player, player, getHealthLost()));
        addToBot(new GainEnergyAction(getEnergyGained()));
        
        if (upgraded)
        {
            addToBot(new ApplyPowerAction(player, player, new ConservePower(player, 1), 1));
        }
    }
}
