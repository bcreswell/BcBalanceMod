//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class DiscoveryImprovedAction extends AbstractGameAction
{
    public static int numPlaced;
    private boolean retrieveCard = false;
    int choiceCount = 0;
    boolean makeItFree = false;
    AbstractCard.CardType cardType;
    AbstractCard.CardRarity cardRarity;
    boolean upgradeCard = false;
    public ArrayList<String> excludedCardIds = new ArrayList<String>();
    
    public DiscoveryImprovedAction(int choiceCount, boolean makeItFree, boolean upgradeCard, AbstractCard.CardType cardType,
                                   AbstractCard.CardRarity cardRarity)
    {
        this.choiceCount = choiceCount;
        this.makeItFree = makeItFree;
        this.upgradeCard = upgradeCard;
        this.cardType = cardType;
        this.cardRarity = cardRarity;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            this.isDone = true;
        }
        else if (this.duration == Settings.ACTION_DUR_FAST)
        {
            AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), CardRewardScreen.TEXT[1], true);
            this.tickDuration();
        }
        else
        {
            if (!this.retrieveCard)
            {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null)
                {
                    AbstractCard discoveryCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    
                    if (makeItFree)
                    {
                        discoveryCard.setCostForTurn(0);
                    }
                    
                    discoveryCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10)
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(discoveryCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    else
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(discoveryCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                
                this.retrieveCard = true;
            }
            
            this.tickDuration();
        }
    }
    
    private ArrayList<AbstractCard> generateCardChoices()
    {
        ArrayList<AbstractCard> choices = new ArrayList<AbstractCard>();
        
        while (choices.size() < choiceCount)
        {
            AbstractCard card = BcUtility.getRandomCard(
                    cardRarity,
                    cardType,
                    false,
                    AbstractDungeon.cardRng);
            
            if (card == null)
            {
                continue;
            }
    
            boolean invalidChoice = false;
            for (String excludedId : excludedCardIds)
            {
                if (card.cardID.equals(excludedId))
                {
                    invalidChoice = true;
                    break;
                }
            }
            
            for (AbstractCard existingCard : choices)
            {
                if (existingCard.cardID.equals(card.cardID))
                {
                    invalidChoice = true;
                    break;
                }
            }
            
            if (!invalidChoice)
            {
                if (upgradeCard)
                {
                    card.upgrade();
                }
                choices.add(card);
            }
        }
        
        return choices;
    }
}