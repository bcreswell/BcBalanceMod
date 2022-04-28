package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class SpotWeakness extends BcSkillCardBase
{
    public static final String ID = "Spot Weakness";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/spot_weakness";
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
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 5;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "If the enemy intends to attack, gain !M! temporary Strength.";
    }
    //endregion
    
    boolean validTargetExists()
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return validTargetExists();
    }
    
//    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
//    {
//        if (!super.canUse(player, monster))
//        {
//            return false;
//        }
//        else
//        {
//            if ((monster == null) || (player == null))
//            {
//                return validTargetExists();
//            }
//
//            if (monster.getIntentBaseDmg() < 0)
//            {
//                cantUseMessage = "Enemy isn't attacking.";
//                return false;
//            }
//            else
//            {
//                return true;
//            }
//        }
//    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
        {
            addToBot(new BcApplyPowerAction(new StrengthPower(player, magicNumber)));
            addToBot(new BcApplyPowerAction(new LoseStrengthPower(player, magicNumber)));
        }
    }
}
