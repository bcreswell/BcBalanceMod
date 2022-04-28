package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class MantraPower extends AbstractPower
{
    public static final String POWER_ID = "Mantra";
    private static final PowerStrings powerStrings;
    private final int PRAYER_REQUIRED = 10;
    
    public MantraPower(AbstractCreature owner, int amount)
    {
        this.name = powerStrings.NAME;
        this.ID = "Mantra";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("mantra");
        this.type = AbstractPower.PowerType.BUFF;
        GameActionManager var10000 = AbstractDungeon.actionManager;
        var10000.mantraGained += amount;
    }
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }
    
    public void updateDescription()
    {
        this.description = powerStrings.DESCRIPTIONS[0] + 10 + powerStrings.DESCRIPTIONS[1] + " NL If you're already in Divinity, NL gain 3 energy instead.";
    }
    
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if (this.amount >= 10)
        {
            //compensate them if they're already in divinity w/ 3 energy.
            if (BcUtility.isPlayerInStance(DivinityStance.STANCE_ID))
            {
                addToTop(new GainEnergyAction(3));
            }
            else
            {
                addToTop(new ChangeStanceAction(DivinityStance.STANCE_ID));
            }
            
            amount -= 10;
            if (amount <= 0)
            {
                addToTop(new RemoveSpecificPowerAction(owner, owner, MantraPower.POWER_ID));
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Mantra");
    }
}
