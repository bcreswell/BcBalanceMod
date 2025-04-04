package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class DieDieDie extends BcAttackCardBase
{
    public static final String ID = "Die Die Die";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/attack/die_die_die";
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
    public int getCost()
    {
        return 2;
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
        return "Deal !D! damage, and again if they're Weak, and again if they're Poisoned.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new DieDieDieEffect(monster.hb.cX, monster.hb.cY, Color.GREEN, Color.WHITE,0), 0.12F));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
 
        AbstractPower weak = monster.getPower(WeakPower.POWER_ID);
        if (weak != null)
        {
            addToBot(new VFXAction(new DieDieDieEffect(monster.hb.cX, monster.hb.cY, Color.GREEN, Color.WHITE,1), 0.12F));
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
        
        AbstractPower poison = monster.getPower(PoisonPower.POWER_ID);
        if (poison != null)
        {
            addToBot(new VFXAction(new DieDieDieEffect(monster.hb.cX, monster.hb.cY, Color.GREEN, Color.WHITE,2), 0.12F));
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }
}
