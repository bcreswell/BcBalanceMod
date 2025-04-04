package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeAllOrbsAction extends AbstractGameAction
{
    public EvokeAllOrbsAction()
    {
        actionType = AbstractGameAction.ActionType.DAMAGE;
    }
    
    public void update()
    {
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i)
        {
            addToTop(new EvokeOrbAction(1));
            addToTop(new TrueWaitAction(.1f));
        }
        
        isDone = true;
    }
}
