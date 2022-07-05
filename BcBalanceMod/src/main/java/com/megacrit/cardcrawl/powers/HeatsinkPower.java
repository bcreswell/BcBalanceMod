//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class HeatsinkPower extends BcPowerBase
{
    public static final String POWER_ID = "Heatsink";
    
    public HeatsinkPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Heatsinks";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "heatsink";
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
            return "Whenever you play a Power, draw #b1 card. NL Conserve up to #b1 left over #yEnergy each turn.";
        }
        else
        {
            return "Whenever you play a Power, draw #b" + amount + " cards. NL Conserve up to #b" + amount + " left over #yenergy each turn.";
        }
    }
    //endregion
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == CardType.POWER)
        {
            flash();
            addToTop(new DrawCardAction(owner, amount));
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            //this shouldn't generate extra energy in the case that you're already conserving it.
            if ((player.getPower(ConserveEnergyPower.POWER_ID) != null) ||
                        (player.getRelic(IceCream.ID) != null))
            {
                return;
            }
            
            int retainEnergyAmt = Math.min(EnergyPanel.totalCount, amount);
            if (retainEnergyAmt > 0)
            {
                flash();
                EnergyPanel.totalCount -= retainEnergyAmt;
                addToBot(new BcApplyPowerAction(new EnergizedBluePower(player, retainEnergyAmt)));
            }
        }
    }
}
