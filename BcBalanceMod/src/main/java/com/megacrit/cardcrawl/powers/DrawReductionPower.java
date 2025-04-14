package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class DrawReductionPower extends AbstractPower 
{
    public static final String POWER_ID = "Draw Reduction";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    int originalCardDraw = 0;
    AbstractPlayer player;

    public DrawReductionPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("lessdraw");
        this.type = PowerType.DEBUFF;
        //this.isTurnBased = true;
        player = AbstractDungeon.player;
    }

    public void onInitialApplication()
    {
        originalCardDraw = player.gameHandSize;
        AbstractDungeon.player.gameHandSize = Math.max(0,originalCardDraw - amount);
    }
    
    @Override
    public void stackPower(int stackAmount)
    {
        fontScale = 8.0F;
        amount += stackAmount;
        player.gameHandSize = Math.max(0, originalCardDraw - amount);
    }
    
    public void atStartOfTurnPostDraw()
    {
        addToBot(new RemoveSpecificPowerAction(player, player, this));
    }
    
    public void onRemove()
    {
        AbstractDungeon.player.gameHandSize = originalCardDraw;
    }

    public void updateDescription()
    {
        description = "Draw "+amount+" less card(s) next turn.";
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}