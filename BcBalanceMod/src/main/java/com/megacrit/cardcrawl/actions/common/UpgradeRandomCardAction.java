package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class UpgradeRandomCardAction extends AbstractGameAction
{
    private AbstractPlayer player;
    
    public UpgradeRandomCardAction()
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        player = AbstractDungeon.player;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FAST)
        {
            if (!player.hand.group.isEmpty())
            {
                CardGroup upgradeable = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                
                for (AbstractCard card : player.hand.group)
                {
                    if (card.canUpgrade())
                    {
                        upgradeable.addToTop(card);
                    }
                }
                
                if (upgradeable.size() > 0)
                {
                    upgradeable.shuffle();
                    AbstractCard cardToUpgrade = upgradeable.group.get(0);
                    cardToUpgrade.upgrade();
                    cardToUpgrade.superFlash();
                    cardToUpgrade.applyPowers();
                }
            }
            
            isDone = true;
        }
        else
        {
            tickDuration();
        }
    }
}
