package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Skewer extends BcAttackCardBase
{
    public static final String ID = "Skewer";

    //region card parameters
    @Override
    public String getDisplayName()
    {
        return ID;
    }

    @Override
    public String getImagePath()
    {
        return "green/attack/skewer";
    }

    @Override
    public int getCost()
    {
        return -1;
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
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public int getDamage()
    {
        return 9;
    }
    
    @Override
    public int getMagicNumber()
    {
        //deal damage x + magicNumber times
        return !upgraded ? 0 : 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 0)
        {
            return "Deal !D! damage X times.";
        }
        else
        {
            return "Deal !D! damage X+!M! times.";
        }
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new SkewerAction(player, monster, damage, damageTypeForTurn, freeToPlayOnce, energyOnUse + magicNumber));
    }
}
