//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.CreativeAIAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class CreativeAIPower extends AbstractPower {
    public static final String POWER_ID = "Creative AI";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    boolean isUpgraded = false;

    public CreativeAIPower(AbstractCreature owner, int amt) {
        this.name = NAME;
        this.ID = "Creative AI";
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("ai");
    }

    //because patcher complaining about me adding a constructor overload
    public void upgrade()
    {
        this.isUpgraded = true;
        this.name = NAME+ (isUpgraded ? "+" : "");
        this.ID = "Creative AI" + (isUpgraded ? "+" : "");
        this.updateDescription();
    }
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            
            for(int i = 0; i < this.amount; ++i)
            {
                this.addToBot(new CreativeAIAction(isUpgraded));
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
            this.description = "Start of turn: Choose 1 of 2 Powers to create.";
        }
        else
        {
             super.updateDescription();
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Creative AI");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
