//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Tactician extends BcSkillCardBase
{
    public static final String ID = "Tactician";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/tactician";
    }
    
    @Override
    public int getCost()
    {
        return -2;
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
    public CardTarget getCardTarget()
    {
        return CardTarget.NONE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When this is discarded, Gain "+getEnergyString(magicNumber)+".";
    }
    //endregion
    
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        cantUseMessage = "Can't play that.";
        return false;
    }
    
    public void triggerOnManualDiscard()
    {
        addToTop(new GainEnergyAction(magicNumber));
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
}
