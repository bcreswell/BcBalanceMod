package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveNextOrbAction extends AbstractGameAction
{
    public RemoveNextOrbAction()
    {
        actionType = ActionType.SPECIAL;
    }
    
    public void update()
    {
        if (AbstractDungeon.player.filledOrbCount() > 0)
        {
            AbstractDungeon.player.removeNextOrb();
        }
        
        isDone = true;
    }
}
