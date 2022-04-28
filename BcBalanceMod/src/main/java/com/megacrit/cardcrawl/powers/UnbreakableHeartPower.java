package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class UnbreakableHeartPower extends AbstractPower
{
    public static final String POWER_ID = "UnbreakableHeartPower";
    private int maxAmt;
    
    public UnbreakableHeartPower(AbstractCreature owner, int amount)
    {
        name = "Unbreakable Heart";
        ID = "UnbreakableHeartPower";
        this.owner = owner;
        this.amount = amount;
        maxAmt = amount;
        updateDescription();
        loadRegion("heartDef");
        priority = 99;
    }
    
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        if (damageAmount > amount)
        {
            damageAmount = amount;
        }
        
        amount -= damageAmount;
        if (amount < 0)
        {
            amount = 0;
        }
        
        updateDescription();
        return damageAmount;
    }
    
    public void atStartOfTurn()
    {
        amount = maxAmt;
        updateDescription();
    }
    
    public void updateDescription()
    {
        if (amount <= 0)
        {
            description = "Can no longer be harmed this turn.";
        }
        else
        {
            description = "Can only lose #b" + amount + " more HP this turn.";
        }
    }
}
