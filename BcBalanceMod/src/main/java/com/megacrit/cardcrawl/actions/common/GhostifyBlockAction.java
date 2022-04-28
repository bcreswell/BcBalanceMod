package com.megacrit.cardcrawl.actions.common;

import com.evacipated.cardcrawl.mod.stslib.blockmods.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GhostifyBlockAction extends AbstractGameAction
{
    AbstractCreature owner;
    
    public GhostifyBlockAction(AbstractCreature owner)
    {
        this.owner = owner;
    }
    
    public void update()
    {
        //if no longer intangible, return block to normal
        if (owner.getPower(IntangiblePlayerPower.POWER_ID) == null)
        {
            AbstractPower ghostlyBlockPower = owner.getPower(GhostlyBlock.POWER_ID);
            if ((ghostlyBlockPower != null) && (ghostlyBlockPower.amount > 0))
            {
                addToBot(new GainBlockAction(owner, ghostlyBlockPower.amount));
                addToBot(new RemoveSpecificPowerAction(owner, owner, GhostlyBlock.POWER_ID));
            }
        }
        else if (owner.currentBlock > 0)
        {
            //otherwise, ghostify their current block
            addToBot(new BcApplyPowerAction(new GhostlyBlock(owner, owner.currentBlock)));
            owner.loseBlock(owner.currentBlock, true);
        }
        
        isDone = true;
    }
}
