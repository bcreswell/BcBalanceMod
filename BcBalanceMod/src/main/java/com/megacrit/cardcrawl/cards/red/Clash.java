//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class Clash extends BcAttackCardBase
{
    public static final String ID = "Clash";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/clash";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
    public int getDamage()
    {
        return !upgraded ? 12 : 16;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "If you have no Skills or Powers in hand, NL Deal !D! damage.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            addToBot(new VFXAction(new ClashEffect(monster.hb.cX, monster.hb.cY), 0.1F));
        }
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.NONE));
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return !hasSkillsOrPowersInHand();
    }
    
    boolean hasSkillsOrPowersInHand()
    {
        for(AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c.type == CardType.POWER) || (c.type == CardType.SKILL))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        if (!super.canUse(player, monster))
        {
            return false;
        }
        else
        {
            for(AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if ((card.type == CardType.SKILL) || (card.type == CardType.POWER))
                {
                    cantUseMessage = "Can't Clash with " + card.name + " in hand.";
                    return false;
                }
            }
        }
        
        return true;
    }
}
