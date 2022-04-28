//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class BodySlam extends BcAttackCardBase
{
    public static final String ID = "Body Slam";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/body_slam";
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return 0;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (BcUtility.isPlayerInCombat())
        {
            if (!upgraded)
            {
                return "Deal !D! damage (150% of your Block), then lose half of your Block.";
            }
            else
            {
                return "Deal !D! damage (200% of your Block), then lose half of your Block.";
            }
        }
        else
        {
            if (!upgraded)
            {
                return "Deal damage equal to 150% of your Block, then lose half of your Block.";
            }
            else
            {
                return "Deal damage equal to 200% of your Block, then lose half of your Block.";
            }
        }
    }
    //endregion
    
    int getBodySlamDamage()
    {
        int bodySlamDmg = 0;
        
        if (BcUtility.isPlayerInCombat())
        {
            bodySlamDmg += AbstractDungeon.player.currentBlock;
            bodySlamDmg += BcUtility.getPowerAmount(GhostlyBlock.POWER_ID);
        }
        
        if (upgraded)
        {
            bodySlamDmg *= 2f;
        }
        else
        {
            bodySlamDmg *= 1.5f;
        }
        
        return bodySlamDmg;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        baseDamage = getBodySlamDamage();
        
        calculateCardDamage(monster);
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
        
        if (AbstractDungeon.player.currentBlock > 1)
        {
            addToBot(new GainBlockAction(player, -AbstractDungeon.player.currentBlock / 2));
        }
        
        int ghostlyBlock = BcUtility.getPowerAmount(GhostlyBlock.POWER_ID);
        if (ghostlyBlock > 1)
        {
            addToBot(new BcApplyPowerAction(new GhostlyBlock(player, -ghostlyBlock / 2)));
        }
    }
    
    public void applyPowers()
    {
        baseDamage = getBodySlamDamage();
        
        super.applyPowers();
        
        rawDescription = getFullDescription();
        initializeDescription();
    }
    
    public void calculateCardDamage(AbstractMonster monster)
    {
        baseDamage = getBodySlamDamage();
        
        super.calculateCardDamage(monster);
        
        rawDescription = getFullDescription();
        initializeDescription();
    }
}
