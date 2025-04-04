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
    int channelCount;
    int triggerPassiveCount;
    boolean evoke;

    public RecursionAction(int channelCount, int triggerPassiveCount, boolean evoke)
    {
        this.channelCount = channelCount;
        this.triggerPassiveCount = triggerPassiveCount;
        this.evoke = evoke;
    }
    
    public void update()
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
            if (!(orb instanceof EmptyOrbSlot))
            {
                for(int i = 0; i < triggerPassiveCount; i++)
                {
                    addToBot(new TriggerOrbPassiveAction(orb));
                    addToBot(new AnimateSpecificOrbAction(orb));
                }

                if (evoke)
                {
                    addToBot(new AnimateSpecificOrbAction(orb));
                    addToBot(new EvokeOrbAction(1));
                }
                else
                {
                    addToBot(new RemoveNextOrbAction());
                }

                if (channelCount > 0)
                {
                    addToBot(new ChannelAction(orb, false));

                    for(int i = 0; i < channelCount - 1; i++)
                    {
                        AbstractOrb newOrb = orb.makeCopy();
                        addToBot(new ChannelAction(newOrb, true));
                    }
                }
            }
        }
        
        isDone = true;
    }
}
