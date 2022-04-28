//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BladeFuryAction extends AbstractGameAction
{
    private boolean isUpgraded;
    
    public BladeFuryAction(boolean upgraded)
    {
        this.isUpgraded = upgraded;
    }
    
    public void update()
    {
        int theSize = AbstractDungeon.player.hand.size();
        if (this.isUpgraded)
        {
            AbstractCard s = (new Shiv()).makeCopy();
            s.upgrade();
            this.addToTop(new MakeTempCardInHandAction(s, theSize));
        }
        else
        {
            this.addToTop(new MakeTempCardInHandAction(new Shiv(), theSize));
        }
        
        if (isUpgraded)
        {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, theSize*2));
        }
        else
        {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, theSize));
        }
        
        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));
        this.isDone = true;
    }
}
