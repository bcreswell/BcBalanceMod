package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardSpecificCardAction extends AbstractGameAction
{
    private AbstractCard targetCard;
    private CardGroup group;
    boolean isEndOfTurnDiscard;
    
    public DiscardSpecificCardAction(AbstractCard targetCard)
    {
        this(targetCard, null, false, false);
    }
    
    public DiscardSpecificCardAction(AbstractCard targetCard, CardGroup group)
    {
        this(targetCard, group, false, false);
    }
    
    public DiscardSpecificCardAction(AbstractCard targetCard, CardGroup group, boolean instant)
    {
        this(targetCard, group, instant, false);
    }
    
    public DiscardSpecificCardAction(AbstractCard targetCard, CardGroup group, boolean instant, boolean isEndOfTurnDiscard)
    {
        this.targetCard = targetCard;
        if (group == null)
        {
            this.group = AbstractDungeon.player.hand;
        }
        else
        {
            this.group = group;
        }
        actionType = AbstractGameAction.ActionType.DISCARD;
        if (instant)
        {
            duration = 0;
        }
        else
        {
            duration = Settings.ACTION_DUR_FAST;
        }
        startDuration = duration;
        this.isEndOfTurnDiscard = isEndOfTurnDiscard;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            if (group.contains(targetCard))
            {
                group.moveToDiscardPile(targetCard);
                if (!isEndOfTurnDiscard)
                {
                    GameActionManager.incrementDiscard(false);
                    targetCard.triggerOnManualDiscard();
                }
            }
        }
        
        tickDuration();
    }
}
