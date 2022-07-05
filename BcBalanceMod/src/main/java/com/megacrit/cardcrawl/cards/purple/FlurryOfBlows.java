package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class FlurryOfBlows extends BcAttackCardBase
{
    public static final String ID = "FlurryOfBlows";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/flurry_of_blows";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean isARetrieveCard()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Whenever you change Stances, Retrieve this card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    
    @Override
    public void onStanceChange(AbstractStance newStance)
    {
        if (AbstractDungeon.player.discardPile.contains(this))
        {
            addToBot(new DiscardToHandAction(this));
        }
    }
}
