package com.megacrit.cardcrawl.relics;

import bcBalanceMod.BcUtility;
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
      return this.DESCRIPTIONS[0]+" NL This includes Hidden Shivs.";
    }
    
    public AbstractRelic makeCopy() {
      return new WristBlade();
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        if (BcUtility.isZeroCostCard(c))
        {
            return  damage + 4.0F;
        }
        else
        {
            return damage;
        }
    }
}
