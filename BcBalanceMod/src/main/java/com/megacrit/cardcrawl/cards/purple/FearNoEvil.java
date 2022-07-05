//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.FearNoEvilAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.*;

public class FearNoEvil extends BcAttackCardBase
{
    public static final String ID = "FearNoEvil";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/fear_no_evil";
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
        return !upgraded ? 6 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL If the enemy intends to Attack, enter Calm.";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        if (!BcUtility.isPlayerInStance(CalmStance.STANCE_ID))
        {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new FearNoEvilAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
    }
}
