//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;
import java.util.Collections;

public class RemoveSpecificOrbAction extends AbstractGameAction
{
    AbstractOrb orbToRemove = null;
    
    public RemoveSpecificOrbAction(AbstractOrb orb)
    {
        this.orbToRemove = orb;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }
    
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
            int removeIndex = 0;
            for (int i = 0; i < orbs.size(); i++)
            {
                if (orbs.get(i) == orbToRemove)
                {
                    removeIndex = i;
                    break;
                }
            }
            
            AbstractOrb emptyOrbSlot = new EmptyOrbSlot(orbToRemove.cX, orbToRemove.cY);
            
            for (int i = removeIndex + 1; i < orbs.size(); ++i)
            {
                Collections.swap(orbs, i, i - 1);
            }
            
            orbs.set(orbs.size() - 1, emptyOrbSlot);
            
            for (int i = 0; i < orbs.size(); ++i)
            {
                ((AbstractOrb) orbs.get(i)).setSlot(i, AbstractDungeon.player.maxOrbs);
            }
        }
        
        this.tickDuration();
    }
}
