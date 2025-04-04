package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.AllCostToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllForOne extends BcAttackCardBase
 {
    public static final String ID = "All For One";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/all_for_one";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public int getDamage()
    {
        return !upgraded ? 10 : 14;
    }
    
    @Override
    public boolean canBeRetrieved()
    {
       return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Retrieve all of your zero cost cards.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new AllCostToHandAction(0));
    }
}
