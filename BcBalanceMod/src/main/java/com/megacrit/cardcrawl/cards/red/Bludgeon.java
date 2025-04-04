package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import com.megacrit.cardcrawl.vfx.stance.LimitBreakParticleEffect;

public class Bludgeon extends BcAttackCardBase
{
    public static final String ID = "Bludgeon";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/bludgeon";
    }
    
    @Override
    public int getCost()
    {
        return 3;
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 40 : 50;
    }

    @Override
    public int getMagicNumber()
    {
        return 1;
    }

    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Suffer !M! Vulnerable.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
        addToBot(new TrueWaitAction(.5F));
        addToBot(new BludgeonAction(monster, new DamageInfo(player, damage, damageTypeForTurn), 1, 1));
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, magicNumber, false)));
    }
}