//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.CreativeAI;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class CreativeAIAction extends AbstractGameAction
{
    public static int numPlaced;
    private boolean retrieveCard = false;
    boolean isUpgraded;
    
    public CreativeAIAction(boolean isUpgraded)
    {
        this.isUpgraded = isUpgraded;
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
                    AbstractCard helloCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    helloCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10)
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(helloCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                    else
                    {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(helloCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
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
        ArrayList choices = new ArrayList();
        
        int choiceCount = 2;
        
        while (choices.size() < choiceCount)
        {
            AbstractCard powerCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            if (powerCard instanceof CreativeAI)
            {
                continue;
            }
    
            boolean dupe = false;
            Iterator cardIterator = choices.iterator();
            while (cardIterator.hasNext())
            {
                AbstractCard existingCard = (AbstractCard) cardIterator.next();
                
                if (existingCard.cardID.equals(powerCard.cardID))
                {
                    dupe = true;
                    break;
                }
            }
            
            if (!dupe)
            {
                choices.add(powerCard);
            }
        }
        
        return choices;
    }
}
