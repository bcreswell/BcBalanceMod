package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import javax.smartcardio.Card;

public class ColdSnap extends BcAttackCardBase
{
    public static final String ID = "Cold Snap";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/cold_snap";
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return 1;
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
        return 1;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 12;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Channel !M! Frost.";
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        
        for (int i = 0; i < magicNumber; ++i)
        {
            addToBot(new ChannelAction(new Frost()));
        }        
    }
}
