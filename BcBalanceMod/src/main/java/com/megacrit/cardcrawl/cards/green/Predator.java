package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PredatorAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class Predator extends BcAttackCardBase
{
    public static final String ID = "Predator";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/predator";
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
        return 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 15 : 20;
    }
    @Override
    public int getMagicNumber()
    {
        //card draw
        return 2;
    }
    
    public int getBonusEnergy()
    {
        return 2;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        //return "Deal !D! damage. NL NL Start of Next Turn: NL Draw " + BcUtility.getCardCountString(magicNumber) + ".";
        return "Deal !D! damage. NL If this kills an enemy, Draw "+getCardCountString(magicNumber)+", and NL Gain "+BcUtility.getEnergyString(getBonusEnergy(),this)+".";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new PredatorAction(monster, new DamageInfo(player, damage, damageTypeForTurn), magicNumber, getBonusEnergy()));
    
//        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_HEAVY));
//        addToBot(new BcApplyPowerAction(new DrawCardNextTurnPower(player, magicNumber)));
    }
}