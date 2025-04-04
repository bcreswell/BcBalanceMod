//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class BcForethoughtAction extends AbstractGameAction 
{
    private AbstractPlayer player;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean chooseAny;
    private boolean upgraded;

    public BcForethoughtAction(boolean upgraded) {
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.chooseAny = true;
        this.upgraded = upgraded;
    }

    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (player.hand.isEmpty())
            {
                isDone = true;
            }
            else if (player.hand.size() == 1 &&
                !chooseAny)
            {
                AbstractCard c = player.hand.getTopCard();
                if (c.cost > 0)
                {
                    c.freeToPlayOnce = true;
                }
                
                if (!upgraded)
                {
                    player.hand.moveToBottomOfDeck(c);
                }
                else
                {
                    //move to top of deck
                    player.hand.moveToDeck(c, false);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                isDone = true;
            }
            else
            {
                if (!upgraded)
                {
                    AbstractDungeon.handCardSelectScreen.open("put on bottom of your draw pile.", 99, true, true);
                }
                else
                {
                    AbstractDungeon.handCardSelectScreen.open("put on top of your draw pile.", 99, true, true);
                }

                tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                if (!upgraded)
                {
                    AbstractCard c;
                    for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); player.hand.moveToBottomOfDeck(c))
                    {
                        c = (AbstractCard) var1.next();
                        if (c.cost > 0)
                        {
                            c.freeToPlayOnce = true;
                        }
                    }
                }
                else
                {
                    AbstractCard c;
                    for (Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); player.hand.moveToDeck(c, false))
                    {
                        c = (AbstractCard) var1.next();
                        if (c.cost > 0)
                        {
                            c.freeToPlayOnce = true;
                        }
                    }
                }

                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ForethoughtAction");
        TEXT = uiStrings.TEXT;
    }
}
