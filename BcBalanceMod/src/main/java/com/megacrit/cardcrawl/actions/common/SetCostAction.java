package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import java.util.Iterator;
import java.util.UUID;

public class SetCostAction extends AbstractGameAction
{
    private AbstractCard card = null;
    
    public SetCostAction(AbstractCard card, int amount)
    {
        this.card = card;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    public void update()
    {
        if (!isDone)
        {
            card.cost = amount;
            if (card.cost < 0)
            {
                card.cost = 0;
            }
            card.costForTurn = card.cost;
            card.isCostModified = false;
            
            isDone = true;
        }
    }
}
