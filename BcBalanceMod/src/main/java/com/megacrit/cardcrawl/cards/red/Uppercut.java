package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Uppercut extends BcAttackCardBase
{
    public static final String ID = "Uppercut";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/uppercut";
    }

    @Override
    public int getCost()
    {
        return 2;
    }

    @Override
    public String getId()
    {
        return ID;
    }

    @Override
    public AbstractCard.CardRarity getCardRarity()
    {
        return AbstractCard.CardRarity.UNCOMMON;
    }

    @Override
    public int getMagicNumber()
    {
        return 2;
    }

    @Override
    public int getDamage()
    {
        return !upgraded ? 8 : 14;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Inflict !M! Weak NL and !M! Vulnerable.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new BcApplyPowerAction(monster, new WeakPower(monster, magicNumber, false)));
        addToBot(new BcApplyPowerAction(monster, new VulnerablePower(monster, magicNumber, false)));
    }
}
