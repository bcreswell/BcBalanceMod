package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.Objects;

public class EvokeNextOrbOfTypeAction extends AbstractGameAction
{
   String orbTypeId;

   public EvokeNextOrbOfTypeAction(String orbTypeId)
   {
      this.orbTypeId = orbTypeId;

      duration = (Settings.FAST_MODE) ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
      startDuration = duration;
   }

   @Override
   public void update()
   {
      //first update
      if (duration == startDuration)
      {
         AbstractOrb orbToEvoke = null;
         for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
         {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(i);
            if (Objects.equals(orb.ID, orbTypeId))
            {
               orbToEvoke = orb;
               break;
            }
         }

         if (orbToEvoke != null)
         {
            addToTop(new EvokeSpecificOrbAction(orbToEvoke));
            addToTop(new AnimateSpecificOrbAction(orbToEvoke));
         }
      }

      tickDuration();
   }
}
