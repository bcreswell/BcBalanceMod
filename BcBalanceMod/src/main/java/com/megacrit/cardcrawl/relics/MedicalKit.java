package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MedicalKit extends AbstractRelic {
    public static final String ID = "Medical Kit";
    
    public MedicalKit() {
        super("Medical Kit", "medicalKit.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription() {
        return "#yStatus cards can now be played at zero cost. Whenever you play a #yStatus card, #yExhaust it.";
    }
    
    public void onDrawOrDiscard()
    {
        for(AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (card.type == AbstractCard.CardType.STATUS)
            {
                if (card.cost > 0)
                {
                    card.cost = 0;
                    card.costForTurn = 0;
                    card.isCostModified = true;
                }
            }
        }
    }
    
    public AbstractRelic makeCopy() {
        return new MedicalKit();
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.STATUS) {
            AbstractDungeon.player.getRelic("Medical Kit").flash();
            card.exhaust = true;
            action.exhaustCard = true;
        }
        
    }
}