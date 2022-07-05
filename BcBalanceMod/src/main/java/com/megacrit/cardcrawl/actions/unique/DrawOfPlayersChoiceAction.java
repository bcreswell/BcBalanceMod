package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

import java.util.Iterator;

public class DrawOfPlayersChoiceAction extends AbstractGameAction
{
    boolean includeExhaustPile;
    boolean onlyEthereal;
    boolean onlySkills;
    boolean onlyAttacks;
    AbstractPlayer player;
    
    public DrawOfPlayersChoiceAction(int amount, boolean includeExhaustPile, boolean onlyEthereal, boolean onlySkills, boolean onlyAttacks)
    {
        this.amount = amount;
        this.includeExhaustPile = includeExhaustPile;
        this.onlyEthereal = onlyEthereal;
        this.onlySkills = onlySkills;
        this.onlyAttacks = onlyAttacks;
        player = AbstractDungeon.player;
        
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = 0;
        startDuration = duration;
    }
    
    public void update()
    {
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            CardGroup possibleCardsToDraw = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            
            for (AbstractCard drawPileCard : player.drawPile.group)
            {
                if (onlyAttacks && (drawPileCard.type != AbstractCard.CardType.ATTACK))
                {
                    continue;
                }
                
                if (onlySkills && (drawPileCard.type != AbstractCard.CardType.SKILL))
                {
                    continue;
                }
                
                if (onlyEthereal && !drawPileCard.isEthereal)
                {
                    continue;
                }
                
                possibleCardsToDraw.addToBottom(drawPileCard);
            }
            
            possibleCardsToDraw.sortAlphabetically(true);
            possibleCardsToDraw.sortByRarityPlusStatusCardType(false);
            
            if (includeExhaustPile)
            {
                for (AbstractCard exhaustPileCard : player.exhaustPile.group)
                {
                    if (onlyAttacks && (exhaustPileCard.type != AbstractCard.CardType.ATTACK))
                    {
                        continue;
                    }
        
                    if (onlySkills && (exhaustPileCard.type != AbstractCard.CardType.SKILL))
                    {
                        continue;
                    }
                    
                    //hardcoded Haunted Whispers "no exhaust" restriction
                    if (onlyEthereal && (!exhaustPileCard.isEthereal || exhaustPileCard.exhaust || exhaustPileCard.exhaustOnUseOnce))
                    {
                        continue;
                    }
        
                    //exhaust pile cards glow to distinguish from draw pile cards
                    exhaustPileCard.glowColor = BcUtility.corruptedGlow;
                    exhaustPileCard.isGlowing = true;
                    exhaustPileCard.unhover();
                    exhaustPileCard.setAngle(0.0F, true);
                    possibleCardsToDraw.addToBottom(exhaustPileCard);
                }
            }
            
            if (possibleCardsToDraw.size() == 0)
            {
                isDone = true;
                return;
            }
            else if (possibleCardsToDraw.size() == 1)
            {
                AbstractCard card = possibleCardsToDraw.getTopCard();
                addToBot(new MoveCardToHandAction(card));
                
                isDone = true;
            }
            else
            {
                String text = "Draw a card of your choice.";
                if (amount != 1)
                {
                    text = "Draw " + amount + " cards of your choice.";
                }
                AbstractDungeon.gridSelectScreen.open(possibleCardsToDraw, amount, text, false);
            }
        }
        else
        {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                addToBot(new MoveCardToHandAction(card));
            }
            
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            isDone = true;
        }
    }
    
}
