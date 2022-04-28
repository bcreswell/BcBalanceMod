//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class ReboundUpgradePower extends AbstractPower
{
    public ReboundUpgradePower(AbstractCreature owner, int amount)
    {
        this.name = "On the Rebound";
        this.ID = "ReboundUpgrade";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("swivel");
        this.isTurnBased = true;
        this.type = PowerType.BUFF;
    }
    
    public void updateDescription()
    {
        if (this.amount > 1)
        {
            this.description = "Upgrade the next #b" + this.amount + " cards drawn.";
        }
        else
        {
            this.description = "Upgrade the next card drawn.";
        }
    }
    
    public void onCardDraw(AbstractCard card)
    {
        if ((amount > 0) && card.canUpgrade())
        {
            --this.amount;
            this.flash();
            
            card.upgrade();
            card.superFlash();
            card.applyPowers();
            
            if (amount == 0)
            {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                updateDescription();
            }
        }
    }
}
