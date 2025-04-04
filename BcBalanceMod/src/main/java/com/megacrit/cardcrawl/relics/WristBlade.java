package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class WristBlade extends AbstractRelic {
   public static final String ID = "WristBlade";

    public WristBlade() {
      super("WristBlade", "wBlade.png", RelicTier.RARE, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription() {
      return this.DESCRIPTIONS[0];
    }
    
    public AbstractRelic makeCopy() {
      return new WristBlade();
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        if ((c.costForTurn == 0) ||
            c.freeToPlayOnce ||
            ((c.cost == -1) && (EnergyPanel.totalCount == 0))) //x-cost with zero energy remaining
        {
            return  damage + 4.0F;
        }
        else
        {
            return damage;
        }
        //return c.costForTurn != 0 && (!c.freeToPlayOnce || c.cost == -1) ? damage : damage + 4.0F;
    }
}
