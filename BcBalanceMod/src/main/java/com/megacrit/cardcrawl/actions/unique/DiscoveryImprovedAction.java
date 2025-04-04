package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
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
    String generatorId;
    boolean includeForeign = false;
    
    public DiscoveryImprovedAction(int choiceCount, boolean makeItFree, boolean upgradeCard, AbstractCard.CardType cardType,
                                   AbstractCard.CardRarity cardRarity)
    {
        this.choiceCount = choiceCount;
        this.makeItFree = makeItFree;
        this.upgradeCard = upgradeCard;
        this.cardType = cardType;
        this.cardRarity = cardRarity;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }
    public DiscoveryImprovedAction(int choiceCount, boolean makeItFree, boolean upgradeCard, AbstractCard.CardType cardType,
                                   AbstractCard.CardRarity cardRarity, boolean includeForeign)
    {
        this.choiceCount = choiceCount;
        this.makeItFree = makeItFree;
        this.upgradeCard = upgradeCard;
        this.cardType = cardType;
        this.cardRarity = cardRarity;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.includeForeign = includeForeign;
    }
    
    public DiscoveryImprovedAction(int choiceCount, boolean makeItFree, boolean upgradeCard, AbstractCard.CardType cardType,
                                   AbstractCard.CardRarity cardRarity, String generatorId)
    {
        this.choiceCount = choiceCount;
        this.makeItFree = makeItFree;
        this.upgradeCard = upgradeCard;
        this.cardType = cardType;
        this.cardRarity = cardRarity;
        this.generatorId = generatorId;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else if (duration == Settings.ACTION_DUR_FAST)
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
                    AbstractCard discoveryCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    
                    if (makeItFree)
                    {
                        discoveryCard.setCostForTurn(0);
                        BcUtility.makeCardTemporary(discoveryCard);
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
                
                retrieveCard = true;
            }
            
            tickDuration();
        }
    }
    
    private ArrayList<AbstractCard> generateCardChoices()
    {
        ArrayList<AbstractCard> choices = BcUtility.getRandomCards(
                cardRarity,
                cardType,
                includeForeign,
                true,
                includeForeign,
                choiceCount,
                false,
                true,
                generatorId);
        
        if (upgradeCard)
        {
            for(AbstractCard card : choices)
            {
                card.upgrade();
            }
        }
        
        return choices;
    }
}