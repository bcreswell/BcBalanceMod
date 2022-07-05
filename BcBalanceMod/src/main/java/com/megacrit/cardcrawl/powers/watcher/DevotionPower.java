package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DevotionPower extends AbstractPower
{
    public static final String POWER_ID = "DevotionPower";
    private static final PowerStrings powerStrings;
    
    public DevotionPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "DevotionPower";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("devotion");
    }
    
    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
    
    public void atStartOfTurnPostDraw()
    {
        flash();
        addToBot(new BcApplyPowerAction(new MantraPower(owner, amount)));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("DevotionPower");
    }
}
