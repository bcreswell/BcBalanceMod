package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ForesightPower extends AbstractPower
{
    public static final String POWER_ID = "WireheadingPower";
    private static final PowerStrings powerStrings;
    
    public ForesightPower(AbstractCreature owner, int scryAmt)
    {
        this.name = powerStrings.NAME;
        this.ID = "WireheadingPower";
        this.owner = owner;
        this.amount = scryAmt;
        this.updateDescription();
        this.loadRegion("wireheading");
    }
    
    public void updateDescription()
    {
        this.description = "Your #yScrying reveals #b" + amount + " more cards.";
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WireheadingPower");
    }
}
