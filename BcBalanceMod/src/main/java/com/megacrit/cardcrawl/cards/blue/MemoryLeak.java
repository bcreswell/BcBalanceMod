//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class MemoryLeak extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("MemoryLeak");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Memory Leak";
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 1;
    }
    
    @Override
    public int getNumberOfOrbsSlotsCreated()
    {
        return 1;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/memoryLeak.png";
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
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public int getDamage()
    {
        return 10;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Evoke your next orb. NL Gain an orb slot.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new TrueWaitAction(.1f));
        
        addToBot(new AnimateOrbAction(1));
        addToBot(new EvokeOrbAction(1));
        addToBot(new TrueWaitAction(.1f));
        
        if (player.maxOrbs < 10)
        {
            addToBot(new IncreaseMaxOrbAction(1));
        }
    }
}
