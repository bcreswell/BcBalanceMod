package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class RetainCardPower extends AbstractPower
{
    public static final String POWER_ID = "Retain Cards";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RetainCardPower(AbstractCreature owner, int numCards)
    {
        name = NAME;
        ID = "Retain Cards";
        this.owner = owner;
        amount = numCards;
        updateDescription();
        loadRegion("retain");
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            int retainableCards = 0;
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if (!card.selfRetain && !card.retain && !card.isEthereal)
                {
                    retainableCards++;
                }
            }
            
            if ((retainableCards > 0) &&
                        !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
                        !AbstractDungeon.player.hasPower("Equilibrium"))
            {
                addToBot(new BcRetainCardsAction(owner, amount));
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Retain Cards");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
