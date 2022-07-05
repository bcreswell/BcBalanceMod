package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ReboundPower extends AbstractPower
{
    public static final String POWER_ID = "Rebound";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean justEvoked = true;
    
    public ReboundPower(AbstractCreature owner)
    {
        this.name = NAME;
        this.ID = "Rebound";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.loadRegion("rebound");
        this.isTurnBased = true;
        this.type = AbstractPower.PowerType.BUFF;
    }
    
    public void updateDescription()
    {
        if (this.amount > 1)
        {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
        else
        {
            this.description = DESCRIPTIONS[0];
        }
    }
    
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        if (this.justEvoked)
        {
            this.justEvoked = false;
        }
        else if ((card.type != AbstractCard.CardType.POWER) && !card.exhaust && !card.exhaustOnUseOnce)
        {
            this.flash();
            action.reboundCard = true;
            this.addToBot(new ReducePowerAction(this.owner, this.owner, "Rebound", 1));
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Rebound"));
        }
        
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rebound");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
