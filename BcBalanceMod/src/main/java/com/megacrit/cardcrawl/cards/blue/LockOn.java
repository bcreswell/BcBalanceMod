//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.LockOnPower;

public class LockOn extends BcAttackCardBase
{
    public static final String ID = "Lockon";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Bullseye";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/attack/lock_on";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 9 : 12;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isFocusZero(),
            "Deal !D! damage. NL Inflict !M! Lock-On. NL #g0 #gFocus: Inflict !M! more.");
    }
    
    @Override
    public String getFootnote()
    {
        return "Attracts #yDark Orbs.";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(monster, player, new LockOnPower(monster, magicNumber), magicNumber));
        
        if (BcUtility.getCurrentFocus() == 0)
        {
            addToBot(new TrueWaitAction(.3F));
            addToBot(new ApplyPowerAction(monster, player, new LockOnPower(monster, magicNumber), magicNumber));
        }
    }
}
