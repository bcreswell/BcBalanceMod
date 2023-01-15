package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class DoomAndGloom extends BcAttackCardBase
{
    public static final String ID = "Doom and Gloom";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/doom_and_gloom";
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
    public int getChanneledOrbCount()
    {
        return 1;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 11 : 17;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to ALL enemies. NL Channel 1 Dark.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(player, new CleaveEffect(), 0.1F));
        
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ChannelAction(new Dark()));
    }
}
