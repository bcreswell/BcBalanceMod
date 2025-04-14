package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class SeeingRed extends BcSkillCardBase
{
    public static final String ID = "Seeing Red";
    
    //region Description
    @Override
    public String getImagePath()
    {
        return "red/skill/seeing_red";
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    int getCardDraw()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        int cardDraw = getCardDraw();
        
        if (cardDraw == 0)
        {
            return "Gain "+getEnergyString(magicNumber)+". NL Suffer 1 Vulnerable.";
        }
        else
        {
            return "Gain "+getEnergyString(magicNumber)+" and NL Draw "+getCardCountString(cardDraw)+". NL Suffer 1 Vulnerable.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int cardDraw = getCardDraw();
        
        addToBot(new GainEnergyAction(magicNumber));
        if (cardDraw > 0)
        {
            addToBot(new DrawCardAction(cardDraw));
        }
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, 1, false)));
    }
}