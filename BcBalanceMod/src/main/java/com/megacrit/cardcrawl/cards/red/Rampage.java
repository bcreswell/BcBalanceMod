//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
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

public class Rampage extends BcAttackCardBase
{
    public static final String ID = "Rampage";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Rampage";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/attack/rampage";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getDamage()
    {
        //8 + 5(8)
        //7(9) + 7(9)
        return !upgraded ? 7 : 9;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 7 : 9;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Increase this card's damage by !M! this combat.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        addToBot(new ModifyDamageAction(uuid, magicNumber));
    }
}
