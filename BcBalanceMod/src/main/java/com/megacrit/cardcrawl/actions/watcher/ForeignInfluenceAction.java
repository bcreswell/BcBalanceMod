package com.megacrit.cardcrawl.actions.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class ForeignInfluenceAction extends AbstractGameAction
{
    private boolean retrieveCard = false;
    private boolean upgraded;
    int choiceCount;
    
    public ForeignInfluenceAction(boolean upgraded)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        this.choiceCount= 3;
    }
    public ForeignInfluenceAction(int choiceCount)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.upgraded = false;
        this.choiceCount = choiceCount;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
            tickDuration();
        }
        else
        {
            if (!retrieveCard)
            {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null)
                {
                    if (AbstractDungeon.player.hand.size() < 10)
                    {
                        AbstractCard foreignCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                        
                        foreignCard.setCostForTurn(0);
                        foreignCard.current_x = -1000.0F * Settings.xScale;
                        
                        BcUtility.makeCardTemporary(foreignCard);
                        
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(foreignCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                
                retrieveCard = true;
            }
            
            tickDuration();
        }
    }
    
    private ArrayList<AbstractCard> generateCardChoices()
    {
        ArrayList<AbstractCard> cardChoices = new ArrayList<>();
        
        while (cardChoices.size() < choiceCount)
        {
//            int roll = AbstractDungeon.cardRandomRng.random(99);
//            AbstractCard.CardRarity cardRarity;
//            if (roll < 55)
//            {
//                cardRarity = AbstractCard.CardRarity.COMMON;
//            }
//            else if (roll < 85)
//            {
//                cardRarity = AbstractCard.CardRarity.UNCOMMON;
//            }
//            else
//            {
//                cardRarity = AbstractCard.CardRarity.RARE;
//            }
            
            AbstractCard potentialCard = BcUtility.getRandomCard(
                null,
                AbstractCard.CardType.ATTACK,
                true,
                false,
                true,
                false);
            
            if (potentialCard.color == AbstractCard.CardColor.PURPLE)
            {
                //not really "foreign" if it gives you a watcher card.
                potentialCard = null;
            }
            
            if (potentialCard != null)
            {
                for (AbstractCard existingCard : cardChoices)
                {
                    if (existingCard.cardID.equals(potentialCard.cardID))
                    {
                        potentialCard = null;
                        break;
                    }
                }
            }
            
            if (potentialCard != null)
            {
                cardChoices.add(potentialCard.makeCopy());
            }
        }
        
        return cardChoices;
    }
}
