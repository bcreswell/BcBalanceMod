package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

import javax.swing.*;
import java.util.*;

public class RemoveAllButOneDarkOrbAction extends AbstractGameAction
{
    public RemoveAllButOneDarkOrbAction()
    {
        this.actionType = ActionType.SPECIAL;
    }
    
    public void update()
    {
        while (AbstractDungeon.player.filledOrbCount() > 0)
        {
            AbstractDungeon.player.removeNextOrb();
        }
        
       
        this.isDone = true;
    }
}
