package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class Outmaneuver extends BcSkillCardBase
{
    public static final String ID = "Outmaneuver";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/outmaneuver";
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
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 7;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL NL Next turn, NL Gain "+getEnergyString(getMagicNumber())+".";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (block > 0)
        {
            addToBot(new GainBlockAction(player, block));
        }
        addToBot(new BcApplyPowerAction(new EnergizedPower(player, magicNumber)));
    }
}
