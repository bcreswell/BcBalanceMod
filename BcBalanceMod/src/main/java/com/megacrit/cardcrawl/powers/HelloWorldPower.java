//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.HelloWorldAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HelloWorldPower extends AbstractPower
{
    public static final String POWER_ID = "Hello";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean isUpgraded;
    
    public HelloWorldPower(AbstractCreature owner, int cardAmt, boolean isUpgraded)
    {
        this.name = NAME + (isUpgraded ? "!" : "");
        this.ID = "HelloWPower" + (isUpgraded ? "+" : "-");
        this.owner = owner;
        this.isUpgraded = isUpgraded;
        this.amount = cardAmt;
        this.updateDescription();
        this.loadRegion("hello");
    }
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            
            for (int i = 0; i < this.amount; ++i)
            {
                this.addToBot(new HelloWorldAction(isUpgraded));
            }
        }
    }
    
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }
    
    public void updateDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            if (this.amount > 1)
            {
                this.description = "Start of turn: choose #b" + this.amount + (isUpgraded ? " upgraded" : "") + " Common cards to create.";
            }
            else
            {
                this.description = "Start of turn: choose #b" + this.amount + (isUpgraded ? " upgraded" : "") + " Common card to create.";
            }
        }
        else
        {
            super.updateDescription();
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Hello");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
