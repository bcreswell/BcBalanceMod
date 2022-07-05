package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class DualWieldAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer player;
    private int dupeAmount = 1;
    private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();
    boolean firstUpdate = true;
    
    public DualWieldAction(AbstractCreature source, int amount)
    {
        setValues(AbstractDungeon.player, source, amount);
        actionType = AbstractGameAction.ActionType.DRAW;
        duration = DURATION_PER_CARD;
        player = AbstractDungeon.player;
        dupeAmount = amount;
    }
    
    public void update()
    {
        if (firstUpdate)
        {
            firstUpdate = false;
            
            for (AbstractCard card : player.hand.group)
            {
                if (!isDualWieldable(card))
                {
                    cannotDuplicate.add(card);
                }
            }
            
            int dualWieldCandidates = player.hand.group.size() - cannotDuplicate.size();
            
            if (dualWieldCandidates == 0)
            {
                isDone = true;
            }
            else if (dualWieldCandidates == 1)
            {
                for (AbstractCard card : player.hand.group)
                {
                    if (isDualWieldable(card))
                    {
                        for (int i = 0; i < dupeAmount; ++i)
                        {
                            dualWieldGivenCard(card);
                        }
                        
                        isDone = true;
                    }
                }
            }
            else if (dualWieldCandidates > 1)
            {
                player.hand.group.removeAll(cannotDuplicate);
                
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    for (int i = 0; i < dupeAmount; ++i)
                    {
                        dualWieldGivenCard(card);
                    }
                }
                
                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                isDone = true;
            }
        }
        
        tickDuration();
    }
    
    void dualWieldGivenCard(AbstractCard card)
    {
        AbstractCard dualWieldedCard = card.makeStatEquivalentCopy();
        dualWieldedCard.glowColor = card.glowColor;
        dualWieldedCard.freeToPlayOnce = true;
        addToTop(new MakeTempCardInHandAction(dualWieldedCard));
    }
    
    private void returnCards()
    {
        for (AbstractCard card : cannotDuplicate)
        {
            player.hand.addToTop(card);
        }
        
        for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
        {
            player.hand.addToTop(card);
        }
        
        player.hand.refreshHandLayout();
        
        for (AbstractCard card : cannotDuplicate)
        {
            if (card instanceof BcCardBase)
            {
                BcCardBase bcCard = (BcCardBase) card;
                bcCard.glowColor = bcCard.defaultGlow;
            }
        }
    }
    
    private boolean isDualWieldable(AbstractCard card)
    {
        return card.type.equals(AbstractCard.CardType.ATTACK);
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = uiStrings.TEXT;
    }
}
