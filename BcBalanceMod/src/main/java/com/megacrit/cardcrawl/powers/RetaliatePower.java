package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class RetaliatePower extends AbstractPower
{
    public static final String POWER_ID = "Retaliate";
    ArrayList<AbstractCreature> alreadyRetaliatedList = new ArrayList<>();
    
    public RetaliatePower(AbstractCreature owner, int amount)
    {
        name = POWER_ID;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("pressure_points");
    }
    
    public void stackPower(int stackAmount)
    {
        fontScale = 8.0F;
        amount += stackAmount;
        updateDescription();
    }
    
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if (!(info.owner instanceof AbstractMonster))
        {
            return damageAmount;
        }
    
        AbstractMonster attackingMonster = (AbstractMonster)info.owner;
        
        if ((attackingMonster != null) &&
            !alreadyRetaliatedList.contains(attackingMonster) &&
            (info.type != DamageInfo.DamageType.THORNS) &&
            (info.type != DamageInfo.DamageType.HP_LOSS) &&
            (attackingMonster != owner))
        
        {
            alreadyRetaliatedList.add(attackingMonster);
            
            flash();
            
            addToBot(new TrueWaitAction(.3f));
            addToBot(new DamageAction(attackingMonster, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        
        return damageAmount;
    }
    
    public void atStartOfTurn()
    {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
    }
    
    public void updateDescription()
    {
        description = "The first time each enemy deals damage to you, deal " + amount + " in return.";
    }
}
