package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class SetupAction extends AbstractGameAction
{
    private AbstractPlayer p;
    boolean putCardOnTop;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    
    public SetupAction()
    {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.putCardOnTop = false;
    }
    public SetupAction(boolean putCardOnTop)
    {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.putCardOnTop = putCardOnTop;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (p.hand.isEmpty())
            {
                isDone = true;
            }
            else if (p.hand.size() == 1)
            {
                AbstractCard c = p.hand.getTopCard();
                if (c.cost > 0)
                {
                    c.freeToPlayOnce = true;
                }
                
                if (putCardOnTop)
                {
                    p.hand.moveToDeck(c, false);
                }
                else
                {
                    p.hand.moveToBottomOfDeck(c);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                isDone = true;
            }
            else
            {
                if (putCardOnTop)
                {
                    AbstractDungeon.handCardSelectScreen.open("put on top of your draw pile.", 1, false);
                }
                else
                {
                    AbstractDungeon.handCardSelectScreen.open("put on the bottom of your draw pile.", 1, false);
                }
                tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    if (c.cost > 0)
                    {
                        c.freeToPlayOnce = true;
                    }
                    
                    if (putCardOnTop)
                    {
                        p.hand.moveToDeck(c, false);
                    }
                    else
                    {
                        p.hand.moveToBottomOfDeck(c);
                    }
                }
                
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            
            tickDuration();
        }
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("SetupAction");
        TEXT = uiStrings.TEXT;
    }
}
