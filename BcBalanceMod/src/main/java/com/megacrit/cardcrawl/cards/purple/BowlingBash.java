package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class BowlingBash extends BcAttackCardBase
{
    public static final String ID = "BowlingBash";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/bowling_bash";
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
        return 1;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 11;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage for each enemy in combat.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int count = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!m.isDeadOrEscaped())
            {
                count++;
                addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        
        if (count >= 3)
        {
            addToBot(new SFXAction("ATTACK_BOWLING"));
        }
    }
}
