//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class RecursionAction extends AbstractGameAction
{
    boolean triggerPassive;
    boolean triggerEvoke;
    
    public RecursionAction(boolean triggerPassive, boolean triggerEvoke)
    {
        this.triggerPassive = triggerPassive;
        this.triggerEvoke = triggerEvoke;
    }
    
    public void update()
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
            if (!(orb instanceof EmptyOrbSlot))
            {
                if (triggerEvoke)
                {
                    addToBot(new EvokeOrbAction(1));
                }
                else
                {
                    addToBot(new RemoveNextOrbAction());
                }
    
                addToBot(new ChannelAction(orb, false));
                
                if (triggerPassive)
                {
                    addToBot(new TriggerOrbPassiveAction(orb));
                    addToBot(new AnimateSpecificOrbAction(orb));
                }
            }
        }
        
        isDone = true;
    }
}
