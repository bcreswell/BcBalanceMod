package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ArtifactPower extends AbstractPower
{
    public static final String POWER_ID = "Artifact";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String Footnote = "Artifact doesn't negate self inflicted debuffs.";
    
    public ArtifactPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Artifact";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("artifact");
        type = AbstractPower.PowerType.BUFF;
    }
    
    public void onSpecificTrigger()
    {
        if (amount <= 0)
        {
            addToTop(new RemoveSpecificPowerAction(owner, owner, "Artifact"));
        }
        else
        {
            addToTop(new ReducePowerAction(owner, owner, "Artifact", 1));
        }
    }
    
    public void updateDescription()
    {
        String debuffString = "debuff";
        if (amount != 1)
        {
            debuffString = "" + amount + " debuffs";
        }
        
        description = "Negates the next " + debuffString + " inflicted by enemies. NL NL (" + Footnote + ")";
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Artifact");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
