package com.megacrit.cardcrawl.powers.watcher;

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
        name = powerStrings.NAME;
        ID = "Mantra";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("mantra");
        type = AbstractPower.PowerType.BUFF;
        GameActionManager var10000 = AbstractDungeon.actionManager;
        var10000.mantraGained += amount;
    }
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }
    
    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0] + 10 + powerStrings.DESCRIPTIONS[1];
    }
    
    @Override
    public void onInitialApplication()
    {
        tryEnterDivinity();
    }
    
    void tryEnterDivinity()
    {
        while (amount >= 10)
        {
            addToTop(new ChangeStanceAction(DivinityStance.STANCE_ID));
            amount -= 10;
            if (amount <= 0)
            {
                addToTop(new RemovePowerIfEmptyAction(owner, MantraPower.POWER_ID));
            }
        }
    }
    
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        tryEnterDivinity();
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Mantra");
    }
}
