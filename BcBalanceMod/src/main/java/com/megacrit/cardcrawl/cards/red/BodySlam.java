//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        return CardRarity.COMMON;
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

    public float getBlockToDmgFactor()
    {
        return 1;
    }

    @Override
    public String getBaseDescription()
    {
        String description = null;

        int blockToLose = getBlockToLose();
        if (BcUtility.isPlayerInCombat())
        {
            description = "Deal your Block as Damage (!D!).";
        }
        else
        {
            description = "Deal your Block as Damage.";
        }
        
        if (blockToLose > 0)
        {
            description += " NL Then lose " + blockToLose + " Block.";
        }
        
        return description;
    }
    //endregion
    
    int getBodySlamDamage()
    {
        int bodySlamDmg = 0;
        
        if (BcUtility.isPlayerInCombat())
        {
            bodySlamDmg += AbstractDungeon.player.currentBlock;
            bodySlamDmg += BcUtility.getPowerAmount(GhostlyBlockPower.POWER_ID);
        }

        bodySlamDmg = (int)(bodySlamDmg * getBlockToDmgFactor());
        
        return bodySlamDmg;
    }
    
    int getBlockToLose()
    {
        return !upgraded ? 5 : 0;
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
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        calculateCardDamage(monster);
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
        
        int blockToRemove = Math.min(getBlockToLose(), player.currentBlock);
        
        if (blockToRemove > 0)
        {
            if (BcUtility.playerHasPower(GhostlyBlockPower.POWER_ID))
            {
                addToBot(new BcApplyPowerAction(new GhostlyBlockPower(player, -blockToRemove)));
            }
            else
            {
                addToBot(new GainBlockAction(player, -blockToRemove));
            }
        }
    }
}
