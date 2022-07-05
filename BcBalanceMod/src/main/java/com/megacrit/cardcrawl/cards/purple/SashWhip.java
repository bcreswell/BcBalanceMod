package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.HeadStompAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.*;

public class SashWhip extends BcAttackCardBase
{
    public static final String ID = "SashWhip";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/sash_whip";
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
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 8 : 12;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Wrath: Inflict !M! Weak.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            addToBot(new ApplyPowerAction(monster, player, new WeakPower(monster, magicNumber, false), magicNumber));
        }
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.isPlayerInStance(WrathStance.STANCE_ID);
    }
}
