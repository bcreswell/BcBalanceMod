package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class WheelKick extends BcAttackCardBase
{
    public static final String ID = "WheelKick";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/wheel_kick";
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
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 12 : 16;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Temporarily reduce the enemy's Strength by !M!.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        applyTempStrengthLoss(magicNumber, player, monster);
    }
    
    void applyTempStrengthLoss(int strengthLoss, AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -strengthLoss), -strengthLoss, true, AbstractGameAction.AttackEffect.NONE));
        
        //only do strength up if the strength down wasn't blocked by artifact
        if (monster.getPower("Artifact") == null)
        {
            addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, strengthLoss), strengthLoss, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
