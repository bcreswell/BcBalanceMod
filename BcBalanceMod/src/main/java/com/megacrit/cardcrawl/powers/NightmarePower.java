package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class NightmarePower extends BcPowerBase
{
    public static final String POWER_ID = "Night Terror";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCard card;
    
    public NightmarePower(AbstractCreature owner, int cardAmt, AbstractCard copyMe)
    {
        super(owner, cardAmt);
        card = copyMe.makeStatEquivalentCopy();
        card.resetAttributes();
        updateDescription();
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Nightmare";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "nightmare";
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.SeparateApplications;
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (card != null)
        {
            return "Create #b" + amount + " " + FontHelper.colorString(card.name, "y") + " cards next turn.";
        }
        else
        {
            return "";
        }
    }
    //endregion
    
    public void atStartOfTurn()
    {
        addToBot(new MakeTempCardInHandAction(card, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Night Terror");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
