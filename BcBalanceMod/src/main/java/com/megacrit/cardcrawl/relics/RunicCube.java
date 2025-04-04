//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class RunicCube extends AbstractRelic {
   public static final String ID = "Runic Cube";
   private static final int NUM_CARDS = 1;

   public RunicCube() {
      super("Runic Cube", "runicCube.png", RelicTier.RARE, LandingSound.FLAT);
   }

   public String getUpdatedDescription() {
      return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
   }

   public void wasHPLost(int damageAmount) {
      if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && damageAmount > 0) {
         this.flash();
         this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
         this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
      }

   }

   public AbstractRelic makeCopy() {
      return new RunicCube();
   }
}
