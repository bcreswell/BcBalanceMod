package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MasterRealityPower extends AbstractPower
{
    public static final String POWER_ID = "MasterRealityPower";
    private static final PowerStrings powerStrings;
    
    public MasterRealityPower(AbstractCreature owner)
    {
        name = powerStrings.NAME;
        ID = "MasterRealityPower";
        this.owner = owner;
        updateDescription();
        loadRegion("master_reality");
        type = AbstractPower.PowerType.BUFF;
    }
    
    public void updateDescription()
    {
        description = powerStrings.DESCRIPTIONS[0];
    }
    
    @Override
    public void onCardCreated(AbstractCard card)
    {
        if ((card.type != AbstractCard.CardType.CURSE) &&
                    (card.type != AbstractCard.CardType.STATUS) &&
                    (card.tempUpgradeCount == 0))
        {
            card.tempUpgradeCount++;
            flash();
            if (!card.upgraded)
            {
                card.upgrade();
            }
            else
            {
                card.modifyCostForCombat(-1);
            }
            card.flash();
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("MasterRealityPower");
    }
}
