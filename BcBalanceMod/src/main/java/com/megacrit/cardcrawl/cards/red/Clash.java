//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import java.util.Iterator;

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
        return !upgraded ? 12 : 18;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Can only be played if you have no Skills, Powers or Curses in hand. NL Deal !D! damage.";
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m != null)
        {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AttackEffect.NONE));
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return !hasSkillsPowersOrCursesInHand();
    }
    
    boolean hasSkillsPowersOrCursesInHand()
    {
        for(AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if ((c.type == CardType.POWER) || (c.type == CardType.SKILL) || (c.type == CardType.CURSE))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if (!super.canUse(p, m))
        {
            return false;
        }
        else
        {
            for(AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if ((c.type == CardType.CURSE) || (c.type == CardType.SKILL) || (c.type == CardType.POWER))
                {
                    this.cantUseMessage = "Can't Clash with " + c.name + " in hand.";
                    return false;
                }
            }
        }
        
        return true;
    }
}
