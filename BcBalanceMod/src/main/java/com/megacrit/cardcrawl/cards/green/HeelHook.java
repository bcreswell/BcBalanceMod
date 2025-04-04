//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.HeelHookAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.Iterator;

public class HeelHook extends BcAttackCardBase
{
    public static final String ID = "Heel Hook";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/heel_hook";
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 6 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL If the enemy is Weak, NL gain [G] and NL draw a card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new HeelHookAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
    }
    
    @Override
    public boolean isGlowingGold()
    {
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped() && monster.hasPower(WeakPower.POWER_ID))
            {
                return true;
            }
        }
        
        return false;
    }
}
