//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.cards.tempCards.Beta;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.cards.tempCards.Omega;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class RefactorAction extends AbstractGameAction
{
    public static int numExhausted;
    boolean isUpgraded = false;
    
    public RefactorAction(boolean isUpgraded)
    {
        this.isUpgraded = isUpgraded;
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
            Iterator cardIterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            ArrayList<AbstractCard> cardList = new ArrayList<AbstractCard>();
            
            while (cardIterator.hasNext())
            {
                cardList.add((AbstractCard) cardIterator.next());
            }
            
            //dont know why i thought reverse was needed
            //Collections.reverse(cardList);
            
            for (int i = 0; i < cardList.size(); i++)
            {
                AbstractCard cardToExhaust = cardList.get(i);
                
                //create a new card of the same color and type
                AbstractCard refactoredCard = getRandomCardByTypeAndColor(cardToExhaust.type, cardToExhaust.color);
                if (isUpgraded || cardToExhaust.upgraded)
                {
                    refactoredCard.upgrade();
                }
                
                player.hand.moveToExhaustPile(cardToExhaust);
                
                refactoredCard.current_x = -1000.0F * Settings.xScale;
                if (AbstractDungeon.player.hand.size() < 10)
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(refactoredCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
                else
                {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(refactoredCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        
        tickDuration();
    }
    
    AbstractCard getRandomCardByTypeAndColor(AbstractCard.CardType type, AbstractCard.CardColor color)
    {
        CardGroup anyCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (Iterator<Map.Entry<String, AbstractCard>> iterator = CardLibrary.cards.entrySet().iterator(); iterator.hasNext(); )
        {
            Map.Entry<String, AbstractCard> keyValuePair = iterator.next();
            AbstractCard card = keyValuePair.getValue();
            
            if ((card.type == type) &&
                        (card.color == color) &&
                        (card.rarity != AbstractCard.CardRarity.BASIC) &&
                        !card.cardID.equals(Expunger.ID) &&
                        !card.cardID.equals(Beta.ID) &&
                        !card.cardID.equals(BecomeAlmighty.ID) &&
                        !card.cardID.equals(FameAndFortune.ID) &&
                        !card.cardID.equals(LiveForever.ID) &&
                        !card.cardID.equals(Omega.ID) &&
                        !card.cardID.equals(RitualDagger.ID))
            {
                anyCard.addToBottom(card);
            }
        }
        
        anyCard.shuffle(AbstractDungeon.cardRng);
        return anyCard.getRandomCard(true).makeCopy();
    }
}
