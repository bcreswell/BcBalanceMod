package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SwivelPower extends AbstractPower
{
    public static final String POWER_ID = "SwivelPower";
    public static final int RefundThreshold = 2;
    
    public SwivelPower(AbstractCreature owner, int amount)
    {
        name = "Swivel";
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("swivel");
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = "Your next Attack that costs 2 or more will refund its energy.";
        }
        else
        {
            description = "Your next " + amount + " Attacks that costs 2 or more will refund its energy.";
        }
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) && (card.costForTurn >= RefundThreshold) && !card.purgeOnUse && (amount > 0))
        {
            flash();
            
            addToBot(new GainEnergyAction(card.costForTurn));
            
            amount--;
            if (amount <= 0)
            {
                addToTop(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
            }
        }
    }
}
