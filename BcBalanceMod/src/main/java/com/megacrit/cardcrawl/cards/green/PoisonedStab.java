//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonedStab extends BcAttackCardBase
{
    public static final String ID = "Poisoned Stab";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/poisoned_stab";
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
        return !upgraded ? 6 : 8;
    }

    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Inflict !M! Poison.";
    }
    //endregion

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(monster, player, new PoisonPower(monster, player, magicNumber), magicNumber));
    }
}
