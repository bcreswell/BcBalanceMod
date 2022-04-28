package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Immolate extends BcAttackCardBase
{
    public static final String ID = "Immolate";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Burn();
    }
    
    @Override
    public String getImagePath()
    {
        return  "red/attack/immolate";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 22 : 30;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Deal !D! damage to ALL enemies. NL Add 2 *Burns into your discard pile.";
        }
        else
        {
            return "Deal !D! damage to ALL enemies. NL Add 2 *Burn+ into your discard pile.";
        }
    }
    //endregion
    
    protected void onUpgraded()
    {
        cardsToPreview.upgrade();
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInDiscardAction(cardsToPreview, 2));
    }
}
