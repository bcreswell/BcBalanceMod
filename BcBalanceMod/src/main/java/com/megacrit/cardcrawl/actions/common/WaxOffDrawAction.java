package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;

public class WaxOffDrawAction extends AbstractGameAction
{
    public WaxOffDrawAction()
    {
        actionType = ActionType.WAIT;
        duration = 0;
    }
    
    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        AbstractPower waxOffPower = player.getPower(WaxOffPower.POWER_ID);
        int availableHandSpace = BcUtility.getAvailableHandSpace();
        
        //check for hand space
        if ((waxOffPower != null) &&
                    (waxOffPower.amount > 0) &&
                    (availableHandSpace > 0))
        {
            waxOffPower.amount--;
            addToBot(new DrawCardAction(1));
            
            if (waxOffPower.amount <= 0)
            {
                addToBot(new RemoveSpecificPowerAction(player, player, WaxOffPower.POWER_ID));
            }
        }
        
        isDone = true;
    }
}
