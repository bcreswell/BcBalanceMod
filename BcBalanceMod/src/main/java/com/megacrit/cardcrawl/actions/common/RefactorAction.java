//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class RefactorAction extends AbstractGameAction
{
    boolean createsUpgradedCards = false;
    
    public RefactorAction(boolean createsUpgradedCards)
    {
        this.createsUpgradedCards = createsUpgradedCards;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (duration == startDuration)
        {
            if (player.hand.size() == 0)
            {
                this.isDone = true;
                return;
            }
            
            AbstractDungeon.handCardSelectScreen.open("Exhaust", 10, true, true);
            tickDuration();
            return;
        }
        
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                cardsToExhaust.add(card);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            
            if (cardsToExhaust.isEmpty())
            {
                return;
            }
            
            ArrayList<AbstractCard> cardsToCreate = new ArrayList<>();
            CardGroup possibleCardsToCreate;
            for (AbstractCard cardToExhaust : cardsToExhaust)
            {
                AbstractCard cardToCreate = null;
                possibleCardsToCreate = getCardsByTypeAndColor(cardToExhaust.type, cardToExhaust.color);
                while (cardToCreate == null)
                {
                    //create a new card of the same color and type
                    cardToCreate = possibleCardsToCreate.getRandomCard(true).makeCopy();
    
                    if (possibleCardsToCreate.size() >= cardsToExhaust.size() * 2)
                    {
                        //ensure the new cards aren't part of the original set
                        for (AbstractCard existingCard : cardsToExhaust)
                        {
                            if (cardToCreate.cardID.equals(existingCard.cardID))
                            {
                                cardToCreate = null;
                            }
                        }
                    }
                    //if there are too few cards to ensure that new cards wont be part of the original set, just check each card individually instead
                    else if (cardToCreate.cardID.equals(cardToExhaust.cardID))
                    {
                        cardToCreate = null;
                    }
                }
                
                if (cardToCreate.canUpgrade() &&
                            (createsUpgradedCards || cardToExhaust.upgraded))
                {
                    cardToCreate.upgrade();
                }
                
                cardsToCreate.add(cardToCreate);
            }
            
            for (AbstractCard cardToExhaust : cardsToExhaust)
            {
                player.hand.moveToExhaustPile(cardToExhaust);
            }
            
            for (AbstractCard cardToCreate : cardsToCreate)
            {
                cardToCreate.current_x = -1000.0F * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < 10)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(cardToCreate, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
                else
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(cardToCreate, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
            }
        }
        
        tickDuration();
    }
    
    CardGroup getCardsByTypeAndColor(AbstractCard.CardType type, AbstractCard.CardColor color)
    {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : CardLibrary.cards.values())
        {
            boolean illAllowIt = false;
            if ((card.type == type) &&
                        (card.color == color) &&
                        (card.rarity != AbstractCard.CardRarity.BASIC))
            {
                if (card.rarity == AbstractCard.CardRarity.SPECIAL)
                {
                    //extra spice!
                    if (card.cardID.equals(Apparition.ID) ||
                                card.cardID.equals(Bite.ID) ||
                                card.cardID.equals(JAX.ID))
                    {
                        illAllowIt = true;
                    }
                }
                else
                {
                    illAllowIt = true;
                }
            }
            
            if (illAllowIt && !card.hasTag(AbstractCard.CardTags.HEALING))
            {
                group.addToBottom(card);
            }
        }
        
        return group;
    }
}
