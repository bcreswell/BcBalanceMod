package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class EnergizedBluePower extends AbstractPower {
    public static final String POWER_ID = "EnergizedBlue";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public EnergizedBluePower(AbstractCreature owner, int energyAmt) {
        this.name = NAME;
        this.ID = "EnergizedBlue";
        this.owner = owner;
        this.amount = energyAmt;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        
        this.updateDescription();
        this.loadRegion("energized_blue");
    }
    
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
        
    }
    
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
        
    }
    
    public void onEnergyRecharge() {
        this.flash();
        AbstractDungeon.player.gainEnergy(this.amount);
        addToBot(new TrueWaitAction(.2f));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "EnergizedBlue"));
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnergizedBlue");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
