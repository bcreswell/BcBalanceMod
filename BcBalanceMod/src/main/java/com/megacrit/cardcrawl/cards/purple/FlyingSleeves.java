package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class FlyingSleeves extends BcAttackCardBase
{
    public static final String ID = "FlyingSleeves";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/flying_sleeves";
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
        return !upgraded ? 5 : 7;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage twice.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            addToBot(new SFXAction("ATTACK_WHIFF_2", 0.3F));
            addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            addToBot(new VFXAction(new AnimatedSlashEffect(monster.hb.cX, monster.hb.cY - 30.0F * Settings.scale, 500.0F, 200.0F, 290.0F, 3.0F, Color.VIOLET, Color.PINK)));
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
        
        if (monster != null)
        {
            addToBot(new SFXAction("ATTACK_WHIFF_1", 0.2F));
            addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            addToBot(new VFXAction(new AnimatedSlashEffect(monster.hb.cX, monster.hb.cY - 30.0F * Settings.scale, 500.0F, -200.0F, 250.0F, 3.0F, Color.VIOLET, Color.PINK)));
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }    
}
