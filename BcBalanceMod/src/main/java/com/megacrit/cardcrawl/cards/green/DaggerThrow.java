//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class DaggerThrow extends BcAttackCardBase
{
    public static final String ID = "Dagger Throw";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Dagger Throw";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/attack/dagger_throw";
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 9 : 11;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Draw "+ BcUtility.getCardCountString(magicNumber) +". NL Discard 1 card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThrowDaggerEffect(monster.hb.cX, monster.hb.cY)));
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
        addToBot(new DrawCardAction(player, magicNumber));
        addToBot(new DiscardAction(player, player, 1, false));
    }
}
