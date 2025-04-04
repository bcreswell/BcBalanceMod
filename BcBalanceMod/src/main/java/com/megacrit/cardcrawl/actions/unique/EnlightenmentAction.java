package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.Iterator;

public class EnlightenmentAction extends AbstractGameAction
{
    private AbstractPlayer p;
    private boolean forCombat = false;
    
    public EnlightenmentAction(boolean forRestOfCombat)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.forCombat = forRestOfCombat;
    }
    
    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            Iterator var1 = p.hand.group.iterator();
            
            while (var1.hasNext())
            {
                AbstractCard c = (AbstractCard) var1.next();
                if (c.costForTurn > 0)
                {
                    c.costForTurn--;
                    c.isCostModifiedForTurn = true;
                }
            }
        }
        
        tickDuration();
    }
}