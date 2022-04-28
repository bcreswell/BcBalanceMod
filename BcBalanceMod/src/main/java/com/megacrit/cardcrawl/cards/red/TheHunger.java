package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class TheHunger extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("TheHunger");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "The Hunger";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/theHunger.png";
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
        return CardRarity.COMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        //healing
        if (!upgraded)
        {
            return 5;
        }
        else
        {
            return 9;
        }
    }
    
    @Override
    public int getDamage()
    {
        if (!upgraded)
        {
            return 13;
        }
        else
        {
            return 17;
        }
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Heal !M! HP. NL Can only be played on enemies that aren't attacking.";
    }
    
    boolean areAnyEnemiesAttackable()
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() < 0))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return areAnyEnemiesAttackable();
    }
    
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        if (!super.canUse(player, monster))
        {
            return false;
        }
        else
        {
            if ((monster == null) || (player == null))
            {
                return areAnyEnemiesAttackable();
            }
            
            if (monster.getIntentBaseDmg() >= 0)
            {
                this.cantUseMessage = "Enemy is attacking.";
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            this.addToBot(new VFXAction(new BiteEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        }
        
        this.addToBot(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new HealAction(player, player, this.magicNumber));
    }
}
