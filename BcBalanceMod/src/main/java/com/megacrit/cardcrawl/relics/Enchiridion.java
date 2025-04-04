//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class Enchiridion extends AbstractRelic {
    public static final String ID = "Enchiridion";

    public Enchiridion() {
        super("Enchiridion", "enchiridion.png", RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return "Start of Combat: Create a random Power. Make it free and temporary.";
    }

    public void atPreBattle() {
        this.flash();
        
        AbstractCard card = BcUtility.getRandomCard(
            null,
            CardType.POWER,
            false,
            true,
            false,
            true);
            
        if (card != null)
        {
            if (card.cost != -1)
            {
                card.setCostForTurn(0);
            }
                BcUtility.makeCardTemporary(card);

            UnlockTracker.markCardAsSeen(card.cardID);
            addToBot(new MakeTempCardInHandAction(card));
        }
    }

    public AbstractRelic makeCopy() {
        return new Enchiridion();
    }
}
