package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

import java.util.*;

public class JustImprovising extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("JustImprovising");
    public static AbstractMonster TempTarget;
    
    ImprovisedDamage dmg;
    ImprovisedPoison psn;
    ImprovisedVulnerable vln;
    ImprovisedBlock blk;
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Just Improvising";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/justImprovising.png";
    }
    
    @Override
    protected void onInitialized()
    {
        dmg = new ImprovisedDamage();
        psn = new ImprovisedPoison();
        vln = new ImprovisedVulnerable();
        blk = new ImprovisedBlock();
        
        if (upgraded)
        {
            dmg.upgrade();
            psn.upgrade();
            vln.upgrade();
            blk.upgrade();
        }
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
        return 4;
    }
    
    @Override
    public String getBaseDescription()
    {
        String dmgString = BcUtility.getModifiedValueString(dmg.baseDamage, dmg.damage);
        String blkString = BcUtility.getModifiedValueString(blk.baseBlock, blk.block);
        
        return "Deal !D! damage, then NL pick one: NL" +
                       " -Deal " + dmgString + " damage. NL " +
                       " -Inflict " + psn.getMagicNumber() + " Poison. NL " +
                       " -Inflict " + vln.getMagicNumber() + " Vulnerable. NL " +
                       " -Gain " + blkString + " Block. ";
    }
    //endregion
    
    @Override
    public void applyPowers()
    {
        dmg.applyPowers();
        psn.applyPowers();
        vln.applyPowers();
        blk.applyPowers();
        
        super.applyPowers();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster monster)
    {
        dmg.calculateCardDamage(monster);
        
        super.calculateCardDamage(monster);
        rawDescription = getFullDescription();
        initializeDescription();
    }
    
    @Override
    public void resetAttributes()
    {
        dmg.resetAttributes();
        psn.resetAttributes();
        vln.resetAttributes();
        blk.resetAttributes();
        
        super.resetAttributes();
    }
    
    @Override
    protected void onUpgraded()
    {
        dmg.upgrade();
        psn.upgrade();
        vln.upgrade();
        blk.upgrade();
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        TempTarget = monster;
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        ArrayList<AbstractCard> choices = new ArrayList();
        
        dmg = (ImprovisedDamage) dmg.makeStatEquivalentCopy();
        psn = (ImprovisedPoison) psn.makeStatEquivalentCopy();
        vln = (ImprovisedVulnerable) vln.makeStatEquivalentCopy();
        blk = (ImprovisedBlock) blk.makeStatEquivalentCopy();
        
        dmg.applyPowers();
        psn.applyPowers();
        vln.applyPowers();
        blk.applyPowers();
        
        dmg.calculateCardDamage(monster);
        
        choices.add(dmg);
        choices.add(psn);
        choices.add(vln);
        choices.add(blk);
        
        addToBot(new ChooseOneAction(choices));
    }
}
