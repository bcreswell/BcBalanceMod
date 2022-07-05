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
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 5 : 7;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
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
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
        {
            addToBot(new BcApplyPowerAction(new StrengthPower(player, magicNumber)));
            addToBot(new BcApplyPowerAction(new LoseStrengthPower(player, magicNumber)));
        }
    }
}
