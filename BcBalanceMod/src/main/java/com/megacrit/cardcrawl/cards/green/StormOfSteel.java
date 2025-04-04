package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcAttackCardBase;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.BladeFuryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class StormOfSteel extends BcAttackCardBase
{
    public static final String ID = "Storm of Steel";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        //return "green/skill/storm_of_steel";
        return "green/stormOfSteel.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
//
//    @Override
//    protected void onInitialized()
//    {
//        cardsToPreview = new Shiv();
//    }
    
//    @Override
//    protected void onUpgraded()
//    {
//        cardsToPreview.upgrade();
//    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 6 : 8;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to ALL enemies 3 times.";
//        if (!upgraded)
//        {
//            return "Discard your hand, then gain 1 Block and create a *Shiv for each discard.";
//        }
//        else
//        {
//            return "Discard your hand, then Gain 1 Block and Create a *Shiv+ for each discard.";
//        }
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //addToBot(new BladeFuryAction(upgraded));
        
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        
        addToBot(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
}
