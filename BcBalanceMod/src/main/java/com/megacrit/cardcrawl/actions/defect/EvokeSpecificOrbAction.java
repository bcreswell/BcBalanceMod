package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;
import java.util.Collections;

public class EvokeSpecificOrbAction extends AbstractGameAction
{
   AbstractOrb orbToEvoke;

   public EvokeSpecificOrbAction(AbstractOrb orbToEvoke)
   {
      this.orbToEvoke = orbToEvoke;

      duration = (Settings.FAST_MODE) ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
      startDuration = duration;
   }

   @Override
   public void update()
   {
      //first update
      if (duration == startDuration)
      {
         ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;

         if (orbs.size() > 0)
         {
            for (int i = 0; i < orbs.size(); i++)
            {
               AbstractOrb orb = orbs.get(i);
               if (orb == orbToEvoke)
               {
                  orb.onEvoke();

                  //push the evoked orb to the last position
                  for (int orbIndex = i; orbIndex < orbs.size() - 1; orbIndex++)
                  {
                     Collections.swap(orbs, orbIndex, orbIndex + 1);
                  }

                  //replace the last orb (our evoked orb) with an empty slot
                  orbs.set(orbs.size() - 1, new EmptyOrbSlot());
                  break;
               }
            }

            //reassign slot numbers to all orbs
            for (int i = 0; i < orbs.size(); ++i)
            {
               AbstractOrb orb = orbs.get(i);
               orb.setSlot(i, AbstractDungeon.player.maxOrbs);
            }
         }
      }

      tickDuration();
   }
}