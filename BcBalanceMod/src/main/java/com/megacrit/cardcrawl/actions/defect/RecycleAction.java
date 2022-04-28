//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.util.Iterator;

public class RecycleAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isUpgraded = false;

    public RecycleAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void upgrade()
    {
        isUpgraded = true;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.p.hand.isEmpty())
            {
                this.isDone = true;
            }
            else if (this.p.hand.size() == 1)
            {
                gainEnergyFromCard(this.p.hand.getBottomCard());

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
                this.tickDuration();
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        }
        else
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
            {
                AbstractCard c;
                for(Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); this.p.hand.moveToExhaustPile(c))
                {
                    c = (AbstractCard)var1.next();
                    gainEnergyFromCard(c);
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }

            this.tickDuration();
        }
    }

    void gainEnergyFromCard(AbstractCard cardToRecycle)
    {
        int energyToGain = 0;

        if (cardToRecycle.costForTurn == -1)
        {
            //x-cost cards
            if (isUpgraded)
            {
                energyToGain = EnergyPanel.getCurrentEnergy();
            }
            else
            {
                energyToGain = EnergyPanel.getCurrentEnergy() - 1;
            }
        }
        else if (cardToRecycle.costForTurn > 0)
        {
            if (isUpgraded)
            {
                energyToGain = cardToRecycle.costForTurn;
            }
            else
            {
                energyToGain =cardToRecycle.costForTurn - 1;
            }
        }

        if (energyToGain > 0)
        {
            this.addToTop(new GainEnergyAction(energyToGain));
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
        TEXT = uiStrings.TEXT;
    }
}
