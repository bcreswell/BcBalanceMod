package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.watcher.*;

public class CollectPower extends AbstractPower
{
    public static final String POWER_ID = "Collect";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public CollectPower(AbstractCreature owner, int numTurns)
    {
        name = NAME;
        ID = "Collect";
        this.owner = owner;
        amount = numTurns;
        isTurnBased = true;
        if (amount >= 999)
        {
            amount = 999;
        }
        
        updateDescription();
        loadRegion("energized_blue");
    }
    
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if (amount >= 999)
        {
            amount = 999;
        }
    }
    
    public void updateDescription()
    {
        String miracleDescription = "#yMiracle";
        if (BcUtility.playerHasPower(MasterRealityPower.POWER_ID))
        {
            miracleDescription += "+";
        }
        
        if (amount == 1)
        {
            description = "At the start of your next turn, put a " + miracleDescription + " into your hand.";
        }
        else
        {
            description = "At the start of your next #b" + amount + " turns, put a " + miracleDescription + " into your hand.";
        }
    }
    
    public void onEnergyRecharge()
    {
        flash();
        AbstractCard card = new Miracle();
        //card.upgrade();
        
        //will be upgraded by master reality:
        addToBot(new MakeTempCardInHandAction(card));
        
        if (amount <= 1)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Collect"));
        }
        else
        {
            addToBot(new ReducePowerAction(owner, owner, "Collect", 1));
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Collect");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
