package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AscensionPower extends AbstractPower
{
    public static final String POWER_ID = "AscensionPower";
    boolean isUpgraded;
    
    public AscensionPower(AbstractCreature owner, int strAmt)
    {
        name = "Ascension";
        ID = POWER_ID;
        this.owner = owner;
        amount = strAmt;
        updateDescription();
        loadRegion("rupture");
    }
    
//    public void upgrade()
//    {
//        if (!isUpgraded)
//        {
//            isUpgraded = true;
//            name += "+";
//            ID += "+";
//            updateDescription();
//        }
//    }
    
    public void updateDescription()
    {
        description = "When you lose HP from a card, gain #b" + amount + " Strength. NL When you play an Attack, heal for #b" + amount + ".";
    }
    
    public void wasHPLost(DamageInfo info, int damageAmount)
    {
        if ((damageAmount > 0) && (info.owner == owner))
        {
            flash();
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
        }
    }
    
    public void onAfterCardPlayed(AbstractCard usedCard)
    {
        if (BcUtility.isPlayerInCombat())
        {
            if (usedCard.type == AbstractCard.CardType.ATTACK)
            {
                addToBot(new HealAction(owner, owner, amount));
            }
        }
    }
}