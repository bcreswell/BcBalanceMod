package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LockOnPower extends AbstractPower
{
    public static final String POWER_ID = "Lockon";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final float MULTIPLIER = 1.5F;
    private static final int MULTI_STR = 50;
    
    public LockOnPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Lockon";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("lockon");
        type = AbstractPower.PowerType.DEBUFF;
        isTurnBased = true;
    }
    
    public void atEndOfRound()
    {
        if (amount == 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Lockon"));
        }
        else
        {
            addToBot(new ReducePowerAction(owner, owner, "Lockon", 1));
        }
    }
    
    public void updateDescription()
    {
        if (owner != null)
        {
            if (amount == 1)
            {
                description = DESCRIPTIONS[0] + 50 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
            }
            else
            {
                description = DESCRIPTIONS[0] + 50 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Lockon");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
