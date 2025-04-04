package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.unique.EstablishmentPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EstablishmentPower extends AbstractPower
{
    public static final String POWER_ID = "EstablishmentPower";
    private static final PowerStrings powerStrings;
    
    public EstablishmentPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "EstablishmentPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("establishment");
        priority = 25;
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = "End of turn: NL Reduce the cost of a random Retained card by 1 for the rest of combat.";
        }
        else
        {
            description = "End of turn: NL Reduce the cost of " + amount + " random Retained cards by 1 for the rest of combat.";
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        
        addToBot(new EstablishmentPowerAction(amount));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EstablishmentPower");
    }
}
