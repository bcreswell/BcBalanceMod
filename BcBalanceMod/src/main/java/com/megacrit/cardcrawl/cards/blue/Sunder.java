package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.SunderAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Sunder extends BcAttackCardBase
{
    public static final String ID = "Sunder";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/sunder";
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
        return 3;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 24 : 32;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL If this kills an enemy, gain [B] [B] [B].";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped())
            {
                calculateCardDamage(monster);
                if (monster.currentHealth < damage)
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
        addToBot(new WaitAction(0.8F));
        addToBot(new SunderAction(monster, new DamageInfo(player, damage, damageTypeForTurn), 3));
    }
}
