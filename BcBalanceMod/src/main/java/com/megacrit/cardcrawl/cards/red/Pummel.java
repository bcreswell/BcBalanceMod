package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Pummel extends BcAttackCardBase
{
    public static final String ID = "Pummel";
    
    @Override
    public String getImagePath()
    {
        return "red/attack/pummel";
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
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getDamage()
    {
        return 3;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage !M! times.";
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 1; i < magicNumber; ++i)
        {
            addToBot(new PummelDamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
        }
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }    
}