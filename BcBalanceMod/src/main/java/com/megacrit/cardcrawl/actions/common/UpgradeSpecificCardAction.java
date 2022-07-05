package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class UpgradeSpecificCardAction extends AbstractGameAction
{
    private AbstractCard cardToUpgrade;
    
    public UpgradeSpecificCardAction(AbstractCard cardToUpgrade)
    {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.cardToUpgrade = cardToUpgrade;
        duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0F)
        {
            if (cardToUpgrade.canUpgrade() && cardToUpgrade.type != AbstractCard.CardType.STATUS)
            {
                cardToUpgrade.upgrade();
                cardToUpgrade.superFlash();
                cardToUpgrade.applyPowers();
            }
            
            isDone = true;
        }
    }
}
