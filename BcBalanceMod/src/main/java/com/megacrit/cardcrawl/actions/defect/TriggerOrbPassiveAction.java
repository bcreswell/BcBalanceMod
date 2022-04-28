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
import java.util.Iterator;

public class TriggerOrbPassiveAction extends AbstractGameAction
{
    AbstractOrb orbToTrigger;
    public TriggerOrbPassiveAction(AbstractOrb orb)
    {
        this.orbToTrigger = orb;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty())
        {
            Iterator var1 = AbstractDungeon.player.orbs.iterator();

            while(var1.hasNext())
            {
                if (this.orbToTrigger == (AbstractOrb)var1.next())
                {
                    orbToTrigger.onStartOfTurn();
                    orbToTrigger.onEndOfTurn();
                    break;
                }
            }
        }

        this.isDone = true;
    }
}
