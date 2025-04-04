package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.TypeCast;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Plasma;

import java.util.ArrayList;

public class CastOrbAction extends AbstractGameAction
{
    int orbIndex;
    AbstractOrb newOrb;
    
    public CastOrbAction(int orbIndex, AbstractOrb newOrb)
    {
        this.orbIndex = orbIndex;
        this.newOrb = newOrb;
        
        duration = (Settings.FAST_MODE) ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        startDuration = duration;
    }
    
    public void update()
    {
        if (startDuration == duration)
        {
            ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
            
            //dont convert the first one. Lets you save an orb of your choice.
            if ((orbIndex >= 0) && (orbIndex < orbs.size()))
            {
                AbstractOrb existingOrb = orbs.get(orbIndex);

                if ((existingOrb.ID != newOrb.ID) &&
                    !(existingOrb instanceof EmptyOrbSlot) &&
                    !(existingOrb instanceof Plasma) &&
                    (!(existingOrb instanceof Dark) || (existingOrb.evokeAmount < TypeCast.LargeDarkThreshold)))
                {
                    orbs.set(orbIndex, newOrb);
                    newOrb.setSlot(orbIndex, AbstractDungeon.player.maxOrbs);
            
                    addToTop(new AnimateSpecificOrbAction(newOrb));
                }
            }
        }
        
        tickDuration();
    }
}