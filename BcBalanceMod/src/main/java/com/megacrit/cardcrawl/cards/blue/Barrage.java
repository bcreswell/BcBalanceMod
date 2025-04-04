package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.defect.BarrageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Frost;

public class Barrage extends BcAttackCardBase
{
    public static final String ID = "Barrage";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Barrage";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/attack/barrage";
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
        return !upgraded ? 4 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage for each of your Orbs. NL";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int orbCount = BcUtility.getFilledOrbSlotCount();
        
        String colorPrefix = "";
        if (damage > baseDamage)
        {
            colorPrefix = "#g";
        }
        else if (damage < baseDamage)
        {
            colorPrefix = "#r";
        }
        
        //bc: other stuff can impact this calculation. doesn't need to be perfect. ex: akabeko, etc.
        int totalBaseDamage = orbCount * damage;
        
        return "!D! x #b" + orbCount + " = " + colorPrefix + totalBaseDamage + " damage";
    }
    //endregion
    
    int getNonFrostOrbCount()
    {
        int orbCount = 0;
        
        if (BcUtility.isPlayerInCombat())
        {
           for(AbstractOrb orb : AbstractDungeon.player.orbs)
            {
                if (!(orb instanceof EmptyOrbSlot) &&
                    !(orb instanceof Frost))
                {
                    orbCount++;
                }
            }
        }
        
        return orbCount;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BarrageAction(monster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL)));
    }
}