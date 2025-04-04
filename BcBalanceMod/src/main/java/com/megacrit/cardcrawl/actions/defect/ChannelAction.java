package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.Iterator;

public class ChannelAction extends AbstractGameAction
{
    private AbstractOrb orbType;
    private boolean autoEvoke;
    
    public ChannelAction(AbstractOrb newOrbType)
    {
        this(newOrbType, true);
    }
    
    public ChannelAction(AbstractOrb newOrbType, boolean autoEvoke)
    {
        duration = (Settings.FAST_MODE) ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        startDuration = duration;
        orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            if (autoEvoke)
            {
                AbstractDungeon.player.channelOrb(orbType);
            }
            else
            {
                for (AbstractOrb o : AbstractDungeon.player.orbs)
                {
                    if (o instanceof EmptyOrbSlot)
                    {
                        AbstractDungeon.player.channelOrb(orbType);
                        break;
                    }
                }
            }
        }
        
        tickDuration();
    }
}
