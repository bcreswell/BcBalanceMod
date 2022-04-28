//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.*;

public class PremeditationAction extends AbstractGameAction
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static int numExhausted;
    public int shivsPerDiscard;
    
    public PremeditationAction(int shivsPerDiscard)
    {
        this.shivsPerDiscard = shivsPerDiscard;
        duration = this.startDuration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.DISCARD;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        if (duration == startDuration) //first update
        {
            if (player.hand.size() == 0)
            {
                isDone = true;
                return;
            }
            
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 10, true, true);
            tickDuration();
        }
        else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            int discardCount = AbstractDungeon.handCardSelectScreen.selectedCards.size();
            if (discardCount > 0)
            {
                int shivCount = discardCount * shivsPerDiscard;
                addToBot(new ApplyPowerAction(player, player, new HiddenShivPower(player, shivCount), shivCount));
                
//                if (blockPerDiscard > 0)
//                {
//                    int blockToGain = blockPerDiscard * discardCount;
//                    addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockToGain));
//                }
                
                for(AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(card);
                    GameActionManager.incrementDiscard(false);
                    card.triggerOnManualDiscard();
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        
        tickDuration();
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = uiStrings.TEXT;
    }
}
