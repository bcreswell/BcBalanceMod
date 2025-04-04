//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class AllCostToHandAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private int costTarget;
    
    public AllCostToHandAction(int costToTarget)
    {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.costTarget = costToTarget;
    }
    
    public void update()
    {
        for(int i = 0; i < p.discardPile.size(); i++)
        {
            AbstractCard card = p.discardPile.getNCardFromTop(i);
            
            if ((card.cost == costTarget) && BcUtility.canBeRetrieved(card))
            {
                addToBot(new DiscardToHandAction(card));
            }
        }
        
        this.tickDuration();
        this.isDone = true;
    }
}