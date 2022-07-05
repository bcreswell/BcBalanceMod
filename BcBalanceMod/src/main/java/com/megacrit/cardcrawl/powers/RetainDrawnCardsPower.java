package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class RetainDrawnCardsPower extends BcPowerBase
{
    //changing the ID because there's a bug in base code where
    // it reduces the stack count a second time if it succeeded in retaining any energy
    public static final String POWER_ID = "RetainDrawnCardsPower";
    
    public RetainDrawnCardsPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Retain Next Card";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "carddraw";
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
        return "The next " + amount + " card(s) drawn will be retained.";
    }
    //endregion
    
    public void onCardDraw(AbstractCard card)
    {
        if (amount > 0)
        {
            amount--;
            card.retain = true;
            card.flash();
            if (amount == 0)
            {
                addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
            }
        }
    }
}
