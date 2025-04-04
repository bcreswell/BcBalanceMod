//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class Inserter extends AbstractRelic {
   public static final String ID = "Inserter";
   private static final int NUM_TURNS = 2;

   public Inserter() {
      super("Inserter", "inserter.png", RelicTier.RARE, LandingSound.SOLID);
   }

   public String getUpdatedDescription() {
      return this.DESCRIPTIONS[0];
   }

   public void onEquip() {
      this.counter = 0;
   }

   public void atTurnStart() {
      if (this.counter == -1) {
         this.counter += 2;
      } else {
         ++this.counter;
      }

      if (this.counter == 2) {
         this.counter = 0;
         this.flash();
         this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
         this.addToBot(new IncreaseMaxOrbAction(1));
      }

   }

   public AbstractRelic makeCopy() {
      return new Inserter();
   }
}
