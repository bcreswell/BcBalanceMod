package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;

public class HauntedWhispersAction extends AbstractGameAction
{
    private AbstractPlayer player;
    private int numCardsToPick;
    boolean isUpgraded;
    
    public HauntedWhispersAction(int numCardsToPick, boolean isUpgraded)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        player = AbstractDungeon.player;
        this.numCardsToPick = numCardsToPick;
        this.isUpgraded = isUpgraded;
    }
    
    void moveCardToHand(AbstractCard card)
    {
        if (player.drawPile.contains(card))
        {
            if (player.hand.size() == 10)
            {
                player.drawPile.moveToDiscardPile(card);
                player.createHandIsFullDialog();
            }
            else
            {
                player.drawPile.moveToHand(card, player.drawPile);
            }
        }
        else if (player.exhaustPile.contains(card))
        {
            if (player.hand.size() == 10)
            {
                player.exhaustPile.moveToDiscardPile(card);
                player.createHandIsFullDialog();
            }
            else
            {
                player.exhaustPile.moveToHand(card, player.exhaustPile);
            }
        }
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            //region first update
            CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard drawPileCard : player.drawPile.group)
            {
                if (drawPileCard.isEthereal)
                {
                    possibleCards.group.add(drawPileCard);
                }
            }
            
            if (isUpgraded)
            {
                for (AbstractCard exhaustPileCard : player.exhaustPile.group)
                {
                    if (exhaustPileCard.isEthereal)
                    {
                        exhaustPileCard.glowColor = BcBalancingScales.corruptedGlow;
                        exhaustPileCard.isGlowing = true;
                        possibleCards.group.add(exhaustPileCard);
                    }
                }
            }
            
            possibleCards.sortAlphabetically(true);
            possibleCards.sortByRarityPlusStatusCardType(false);
            
            if (!possibleCards.isEmpty())
            {
                if (possibleCards.size() <= numCardsToPick)
                {
                    while (!possibleCards.isEmpty())
                    {
                        AbstractCard topCard = possibleCards.getTopCard();
                        possibleCards.removeTopCard();
                        
                        moveCardToHand(topCard);
                    }
                    player.hand.refreshHandLayout();
                    
                    isDone = true;
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(possibleCards, numCardsToPick, "Pick " + numCardsToPick, false, false, true, false);
                    
                    tickDuration();
                }
            }
            else
            {
                isDone = true;
            }
            //endregion
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    moveCardToHand(card);
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                player.hand.refreshHandLayout();
            }
            
            tickDuration();
        }
    }
}
