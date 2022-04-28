package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FreeAttackPower extends AbstractPower
{
    public static final String POWER_ID = "FreeAttackPower";
    private static final PowerStrings powerStrings;
    
    public FreeAttackPower(AbstractCreature owner, int amount)
    {
        name = powerStrings.NAME;
        ID = "FreeAttackPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("swivel");
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = "The next Attack you play that costs 2 or more, now costs 0.";
        }
        else
        {
            description = "The next " + amount + " Attacks you play that costs 2 or more, now costs 0.";
        }
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) && (card.costForTurn >= 2) && !card.purgeOnUse && (amount > 0))
        {
            flash();
            amount--;
            if (amount == 0)
            {
                addToTop(new RemoveSpecificPowerAction(owner, owner, "FreeAttackPower"));
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("FreeAttackPower");
    }
}
