//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class DivinityNextTurnPower extends AbstractPower
{
    public static final String POWER_ID = "DivinityNextTurnPower";
    
    public DivinityNextTurnPower(AbstractCreature owner, int amount)
    {
        name = "More powerful than you can possibly imagine";
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.loadRegion("focus");
    }
    
    public void updateDescription()
    {
        description = "Start of Next Turn: NL Enter Divinity.";
    }
    
    public void atStartOfTurn()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (amount > 0)
        {
            amount--;
            
            addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
            
            if (amount <= 0)
            {
                addToTop(new RemoveSpecificPowerAction(player, player, POWER_ID));
            }
        }
    }
}
