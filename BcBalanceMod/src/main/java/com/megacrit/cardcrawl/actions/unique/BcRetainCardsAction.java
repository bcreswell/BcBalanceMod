package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BcRetainCardsAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public BcRetainCardsAction(AbstractCreature source, int amount)
    {
        setValues(AbstractDungeon.player, source, amount);
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }
    
    public void update()
    {
        if (duration == 0.5F)
        {
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if (card.selfRetain || card.retain || card.isEthereal)
                {
                    //dim to indicate they're not good retain targets
                    card.isDimmed = true;
                }
            }
            
            AbstractDungeon.handCardSelectScreen.open("Retain.", amount, false, true, false, false, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    if (!card.isEthereal)
                    {
                        card.retain = true;
                    }
                    AbstractDungeon.player.hand.addToTop(card);
                }
                
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                card.isDimmed = false;
            }
            
            tickDuration();
        }
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
        TEXT = uiStrings.TEXT;
    }
}
