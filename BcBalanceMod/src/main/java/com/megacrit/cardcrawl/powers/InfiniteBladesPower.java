//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class InfiniteBladesPower extends AbstractPower
{
    public static final String POWER_ID = "Infinite Blades";
    private static final PowerStrings powerStrings;
    
    public InfiniteBladesPower(AbstractCreature owner, int amount)
    {
        this.name = powerStrings.NAME;
        this.ID = "Infinite Blades";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("infiniteBlades");
    }
    
    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            
            addToBot(new ApplyPowerAction(owner, owner, new HiddenShivPower(owner, amount), amount));
        }
    }
    
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
    
    public void updateDescription()
    {
        if (this.amount == 1)
        {
            description = "Start of turn: NL Create " + amount + " Hidden Shiv";
        }
        else
        {
            description = "Start of turn: NL Create " + amount + " Hidden Shivs";
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Infinite Blades");
    }
}
