//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.BcPowerCardBase;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoopPower;

public class Loop extends BcPowerCardBase
{
   public static final String ID = "Loop";
   
   //region card parameters
   @Override
   public String getImagePath()
   {
      return "blue/power/loop";
   }
   
   @Override
   public String getId()
   {
      return ID;
   }
   
   @Override
   public CardRarity getCardRarity()
   {
      return CardRarity.UNCOMMON;
   }
   
   @Override
   public int getCost()
   {
      return 1;
   }
   
   @Override
   public int getMagicNumber()
   {
      return !upgraded ? 1 : 2;
   }
   
   @Override
   public String getBaseDescription()
   {
      if (magicNumber == 1)
      {
         return "End of Turn: NL Trigger the passive on your next orb. NL NL (Start of Turn for NL Plasma Orbs)";
      }
      else
      {
         return "End of Turn: NL Trigger the passive on your next orb !M! times. NL NL (Start of Turn for NL Plasma Orbs)";
      }
   }
   //endregion
   
   public void use(AbstractPlayer player, AbstractMonster monster)
   {
      addToBot(new BcApplyPowerAction(new LoopPower(player, magicNumber)));
   }
}