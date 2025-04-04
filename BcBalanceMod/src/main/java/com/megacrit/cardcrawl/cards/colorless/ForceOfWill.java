package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class ForceOfWill extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("ForceOfWill");
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/forceOfWill.png";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Force of Will";
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
    public int getBlock()
    {
        return !upgraded ? 8 : 12;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict !M! Weak on ALL enemies. NL Gain !B! Block for each enemy that intends to attack you.";
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ALL_ENEMY;
    }
    //endregion
    
    int getAttackingMonstersCount()
    {
        int count = 0;
        if (BcUtility.isPlayerInCombat())
        {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
                {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        int attackingMonstersCount = getAttackingMonstersCount();
        
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            addToBot(new BcApplyPowerAction(monster, new WeakPower(monster, magicNumber, false)));
        }
        
        for (int i = 0; i < attackingMonstersCount; i++)
        {
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
        }
    }
}
