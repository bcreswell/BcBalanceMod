package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.Iterator;

public class AccuracyPower extends BcPowerBase
{
    public static final String POWER_ID = "Accuracy";
    
    public AccuracyPower(AbstractCreature owner, int amt)
    {
        super(owner, amt);
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Accuracy";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "accuracy";
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
        return "#yShivs and #yHidden #yShivs deal #b" + amount + " additional damage.";
    }
    //endregion
    
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card)
    {
        if (card.cardID.equals(Shiv.ID))
        {
            return damage + amount;
        }
        
        return damage;
    }
}
