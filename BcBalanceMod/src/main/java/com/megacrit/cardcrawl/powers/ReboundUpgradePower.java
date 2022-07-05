//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class ReboundUpgradePower extends BcPowerBase
{
    public static final String POWER_ID = "ReboundUpgradePower";
    
    public ReboundUpgradePower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "On the Rebound";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "reboundUpgrade32x32.png";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (amount == 1)
        {
            return "Upgrade the next card drawn.";
        }
        else
        {
            return "Upgrade the next #b" + amount + " cards drawn.";
        }
    }
    //endregion
    
    public void onCardDraw(AbstractCard card)
    {
        if ((amount > 0) && card.canUpgrade())
        {
            amount--;
            flash();
            addToBot(new UpgradeSpecificCardAction(card));
            addToBot(new RemovePowerIfEmptyAction(owner, POWER_ID));
        }
    }
}
