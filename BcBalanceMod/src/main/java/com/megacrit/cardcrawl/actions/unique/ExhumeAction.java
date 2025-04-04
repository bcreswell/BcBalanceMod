package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhumeAction extends AbstractGameAction
{
    private AbstractPlayer player;
    private final boolean upgraded;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    CardGroup exhumeCandidates = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    int numCardsToExhume = 1;
    
    public ExhumeAction(boolean upgraded, int numCardsToExhume)
    {
        this.upgraded = upgraded;
        this.numCardsToExhume = numCardsToExhume;
        
        player = AbstractDungeon.player;
        setValues(player, AbstractDungeon.player, amount);
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        AbstractCard derp;
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (player.exhaustPile.isEmpty())
            {
                isDone = true;
            }
            else
            {
                for(AbstractCard exhastedCard :player.exhaustPile.group)
                {
                    exhastedCard.stopGlowing();
                    exhastedCard.unhover();
                    exhastedCard.unfadeOut();
                    
                    if (!exhastedCard.cardID.equals("Exhume"))
                    {
                        exhumeCandidates.addToHand(exhastedCard);
                    }
                }
                
                if (exhumeCandidates.isEmpty())
                {
                    isDone = true;
                }
                else
                {
                    numCardsToExhume = Math.min(numCardsToExhume, exhumeCandidates.size());
                    boolean anyNum = numCardsToExhume > 1 ? true : false;
                    AbstractDungeon.gridSelectScreen.open(exhumeCandidates, numCardsToExhume, anyNum, TEXT[0]);
                    tickDuration();
                }
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                {
                    if (upgraded)
                    {
                        card.modifyCostForCombat(-1);
                    }
                    
                    if (player.hand.size() == 10)
                    {
                        player.discardPile.addToHand(card);
                        player.createHandIsFullDialog();
                    }
                    else
                    {
                        player.hand.addToHand(card);
                    }
                    
                    player.exhaustPile.removeCard(card);
                }
                
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                player.hand.refreshHandLayout();
    
                for(AbstractCard card : exhumeCandidates.group)
                {
                    card.unhover();
                }
            }
            
            tickDuration();
        }
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
