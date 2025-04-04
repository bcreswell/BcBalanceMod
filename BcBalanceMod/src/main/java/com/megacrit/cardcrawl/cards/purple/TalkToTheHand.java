package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;

public class TalkToTheHand extends BcAttackCardBase
{
    public static final String ID = "TalkToTheHand";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/talk_to_the_hand";
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
        return 7;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When the target enemy takes attack damage this turn, gain !M! block. NL NL Deal !D! damage. ";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(monster, player, new BlockReturnPower(monster, magicNumber), magicNumber));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}
