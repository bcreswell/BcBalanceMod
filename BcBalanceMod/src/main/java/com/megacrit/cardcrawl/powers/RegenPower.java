package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.RegenAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class RegenPower extends AbstractPower
{
    public static final String POWER_ID = "Regeneration";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static long regenIdOffset = 0;
    
    public RegenPower(AbstractCreature owner, int heal)
    {
        this.name = NAME;
        //adding the regen id on the end ensures each application is a separate buff
        this.ID = "Regeneration"+ regenIdOffset;
        regenIdOffset++;
        this.owner = owner;
        this.amount = heal;
        this.updateDescription();
        this.loadRegion("regen");
        this.type = AbstractPower.PowerType.BUFF;
    }
    
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        flashWithoutSound();
        RegenAction regenAction = new RegenAction(this.owner, this.amount);
        regenAction.regenId = ID;
        addToTop(regenAction);
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Regeneration");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
