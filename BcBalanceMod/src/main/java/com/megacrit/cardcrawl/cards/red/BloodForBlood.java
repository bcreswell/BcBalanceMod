package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodForBlood extends BcAttackCardBase
{
    public static final String ID = "Blood for Blood";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/blood_for_blood";
    }

    @Override
    public int getCost()
    {
        return !upgraded ? 4 : 3;
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
    public int getDamage()
    {
        return !upgraded ? 18 : 22;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Costs 1 less [R] NL for each time you lose HP.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_HEAVY));
    }

    public void tookDamage()
    {
        updateCost(-1);
    }
}
