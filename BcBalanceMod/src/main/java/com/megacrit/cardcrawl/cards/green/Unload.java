package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.UnloadAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class Unload extends BcAttackCardBase
{
    public static final String ID = "Unload";
   
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/unload";
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
    public CardTarget getCardTarget()
    {
        return CardTarget.NONE;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage !M! times to random enemies. NL NL Discard all non-Attack cards in your hand.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 0; i < magicNumber; i++)
        {
            addToBot(new AttackDamageRandomEnemyAction(this,AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        
        addToBot(new UnloadAction(player));
    }
}
