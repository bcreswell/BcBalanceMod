package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.DrawFromDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import org.lwjgl.opengl.AMDDebugOutput;

public class MagnetismPower extends AbstractPower
{
    public static final String POWER_ID = "Magnetism";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String SINGULAR_DESCRIPTION;
    public static final String PLURAL_DESCRIPTION;
    boolean isUpgraded = false;
    
    public MagnetismPower(AbstractCreature owner, int cardAmount)
    {
        this.name = NAME;
        this.ID = "Magnetism";
        this.owner = owner;
        this.amount = cardAmount;
        this.updateDescription();
        this.loadRegion("magnet");
    }
    
    /*
    public void upgrade()
    {
        this.name = NAME+"+";
        this.ID = "Magnetism+";
        isUpgraded = true;
        this.updateDescription();
    }*/
    
    public void atStartOfTurnPostDraw()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.flash();
            
            for (int i = 0; i < this.amount; ++i)
            {
                addToBot(new TrueWaitAction(.3f));
                addToBot(new DrawFromDiscardAction());
                /*
                AbstractCard card = AbstractDungeon.returnTrulyRandomColorlessCardInCombat().makeCopy();
                if (isUpgraded)
                {
                    card.upgrade();
                }
                this.addToBot(new MakeTempCardInHandAction(card, 1, false));
                */
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
        if (amount == 1)
        {
            this.description = "Start of turn: NL Draw " + amount + " card randomly from your discard pile.";
        }
        else
        {
            this.description = "Start of turn: NL Draw " + amount + " cards randomly from your discard pile.";
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Magnetism");
        NAME = powerStrings.NAME;
        SINGULAR_DESCRIPTION = powerStrings.DESCRIPTIONS[0];
        PLURAL_DESCRIPTION = powerStrings.DESCRIPTIONS[1];
    }
}
