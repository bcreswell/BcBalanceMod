package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.DropkickAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.Iterator;

public class Dropkick extends BcAttackCardBase
{
    public static final String ID = "Dropkick";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/drop_kick";
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
    public int getDamage()
    {
        return !upgraded ? 5 : 8;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL If the enemy has Vulnerable, NL gain [R] and NL draw 1 card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DropkickAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
    }
    
    @Override
    public boolean isGlowingGold()
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped() && monster.hasPower(VulnerablePower.POWER_ID))
            {
                return true;
            }
        }
        
        return false;
    }
}
