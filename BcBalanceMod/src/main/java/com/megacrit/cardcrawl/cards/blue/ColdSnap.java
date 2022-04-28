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
    public void onInitialized()
    {
        this.showEvokeOrbCount = 1;
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
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public int getDamage()
    {
        if (upgraded)
        {
            return 11;
        }
        else
        {
            return 7;
        }
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        
        for (int i = 0; i < this.magicNumber; ++i)
        {
            this.addToBot(new ChannelAction(new Frost()));
        }
        
    }
}
