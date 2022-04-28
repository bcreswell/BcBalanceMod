//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class WaxOffPower extends AbstractPower
{
    public static final String POWER_ID = "WaxOffPower";
    boolean isDuringTurn = true; //needs to start in true state in case they discard on the same turn its played
    int previousDiscardCount = 0;
    
    public WaxOffPower(AbstractCreature owner, int amount)
    {
        this.name = "Wax Off";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        previousDiscardCount = GameActionManager.totalDiscardedThisTurn;
        this.loadRegion("wave_of_the_hand");
    }
    
    public void onDrawOrDiscard()
    {
        if (isDuringTurn)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            int discardCount = GameActionManager.totalDiscardedThisTurn;
            if (discardCount > previousDiscardCount)
            {
                int toDraw = discardCount - previousDiscardCount;
                previousDiscardCount = discardCount;
                
                for(int i =0 ;i < toDraw; i++)
                {
                    addToBot(new WaxOffDrawAction());
                }
            }
        }
    }
    
    public void atStartOfTurnPostDraw()
    {
        previousDiscardCount = GameActionManager.totalDiscardedThisTurn;
        isDuringTurn = true;
    }
    
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer)
    {
        isDuringTurn = false;
    }
    
    public void updateDescription()
    {
        description = "Remaining Draw per Discard NL for this turn: "+ amount;
    }
}
