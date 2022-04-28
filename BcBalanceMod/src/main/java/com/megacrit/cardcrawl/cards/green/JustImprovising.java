package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.optionCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import java.util.*;

public class JustImprovising extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("JustImprovising");
    public static AbstractMonster TempTarget;
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "   Just Improvising";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/justImprovising.png";
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 4 : 5;
    }
    
    static int getImprovisedDamage(boolean isUpgraded, AbstractMonster monster)
    {
        int dmg = !isUpgraded ? 4 : 7;
        
        if (monster != null)
        {
            //using a strike to calculate the damage taking all
            // buffs and debuffs into account from player and monster.
            Strike_Green strike = new Strike_Green();
            strike.baseDamage = dmg;
            strike.calculateCardDamage(monster);
            return strike.damage;
        }
        else
        {
            return dmg;
        }
    }
    
    static int getImprovisedPoison(boolean isUpgraded)
    {
        return !isUpgraded ? 3 : 4;
    }
    
    static int getImprovisedVulnerable(boolean isUpgraded)
    {
        return !isUpgraded ? 1 : 2;
    }
    
    static int getImprovisedBlock(boolean isUpgraded)
    {
        return !isUpgraded ? 5 : 7;
    }
    
    @Override
    public int getBlock()
    {
        return getImprovisedBlock(upgraded);
    }
    
    @Override
    public String getBaseDescription()
    {
        AbstractMonster monster = null;
        if (BcUtility.isPlayerInCombat())
        {
            monster = TempTarget;
        }
        
        return "Deal !D! damage, NL then pick one: NL" +
                       " -Deal " + getImprovisedDamage(upgraded, monster) + " damage. NL " +
                       " -Inflict " + getImprovisedPoison(upgraded) + " Poison. NL " +
                       " -Inflict " + getImprovisedVulnerable(upgraded) + " Vulnerable. NL " +
                       " -Gain " + getBlock() + " Block. ";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        TempTarget = monster;
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        ArrayList<AbstractCard> choices = new ArrayList();
        
        AbstractCard dmg = new ImprovisedDamage();
        ImprovisedPoison psn = new ImprovisedPoison();
        ImprovisedVulnerable vln = new ImprovisedVulnerable();
        ImprovisedBlock blk = new ImprovisedBlock();
        
        if (upgraded)
        {
            dmg.upgrade();
            psn.upgrade();
            vln.upgrade();
            blk.upgrade();
        }
        
        choices.add(dmg);
        choices.add(psn);
        choices.add(vln);
        choices.add(blk);
        
        addToBot(new ChooseOneAction(choices));
    }
}
