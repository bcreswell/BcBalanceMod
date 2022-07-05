//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.FlechetteAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class Flechettes extends BcAttackCardBase
{
    public static final String ID = "Flechettes";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/flechettes";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 4 : 6;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage for each Skill card in hand.";
    }
    //endregion
    
    @Override
    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
    }
    
    @Override
    public void calculateDamageDisplay(AbstractMonster mo)
    {
        super.calculateDamageDisplay(mo);
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int skillCount = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (card.type == CardType.SKILL)
            {
                skillCount++;
            }
        }
        
        String colorPrefix = "";
        if (damage > baseDamage)
        {
            colorPrefix = "#g";
        }
        else if (damage < baseDamage)
        {
            colorPrefix = "#r";
        }
        
        //bc: other stuff can impact this calculation. doesn't need to be perfect. ex: akabeko, hidden shiv, etc.
        int totalBaseDamage = skillCount * damage;
        
        return "!D! x #b" + skillCount + " = " + colorPrefix + totalBaseDamage + " damage";
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new FlechetteAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
    }
}
