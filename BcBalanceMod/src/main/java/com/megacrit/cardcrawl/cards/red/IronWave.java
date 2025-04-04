//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

public class IronWave extends BcAttackCardBase
{
    public static final String ID = "Iron Wave";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Iron Wave";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/attack/iron_wave";
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
    public int getDamage()
    {
        return !upgraded ? 5 : 7;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 4;
    }

    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return " Deal !D! damage. NL Gain !B! Block. NL Gain !M! Block at the end of each turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new IronWaveEffect(player.hb.cX, player.hb.cY, monster.hb.cX), 0.5F));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_VERTICAL));
        
        addToBot(new TrueWaitAction(0.1F));
        addToBot(new GainBlockAction(player, player, block));
    
        addToBot(new BcApplyPowerAction(new MetallicizePower(player, getMagicNumber())));
    }
}
