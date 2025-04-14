package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.BcPowerBase;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class DashPower extends BcPowerBase
{
    public static final String POWER_ID = "DashPower";
    
    public DashPower(int amount)
    {
        super(AbstractDungeon.player, amount);
    }
    
    //region card parameters
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Dash";
    }
    
    @Override
    public String getImagePath()
    {
        return "vigor";
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
        return "Your next Attack deals #b" + amount + " additional damage.";
    }
    //endregion
    
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (type == DamageInfo.DamageType.NORMAL)
        {
            return damage + (float)amount;
        }
        
        return damage;
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}