package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class RemoveSomeBlockAction extends AbstractGameAction {
    private static final float DUR = 0.25F;
    private int blockToRemove;

    public RemoveSomeBlockAction(AbstractCreature target, AbstractCreature source, int blockToRemove) {
        this.setValues(target, source, this.amount);
        this.actionType = ActionType.BLOCK;
        this.blockToRemove = blockToRemove;
        this.duration = 0.25F;
    }

    public void update() 
    {
        if (!target.isDying && 
            !target.isDead && 
            duration == 0.25F &&
            target.currentBlock > 0)
        {
            int amountToLose = Math.min(blockToRemove, target.currentBlock);
            target.loseBlock(amountToLose);
        }

        tickDuration();
    }
}