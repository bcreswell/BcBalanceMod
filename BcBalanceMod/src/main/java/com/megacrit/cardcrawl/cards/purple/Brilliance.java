package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Brilliance extends BcAttackCardBase
{
    public static final String ID = "Brilliance";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/brilliance";
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
    public int getDamage()
    {
        return !upgraded ? 9 : 14;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Deals additional damage equal to Mantra gained this combat.";
    }
    //endregion
    
    public void applyPowers()
    {
        int realBaseDamage = baseDamage;
        baseDamage += AbstractDungeon.actionManager.mantraGained;
        super.applyPowers();
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }
    
    public void calculateCardDamage(AbstractMonster mo)
    {
        int realBaseDamage = baseDamage;
        baseDamage += AbstractDungeon.actionManager.mantraGained;
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;
        isDamageModified = damage != baseDamage;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        damage += AbstractDungeon.actionManager.mantraGained;
        calculateCardDamage(monster);
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
    }
}
