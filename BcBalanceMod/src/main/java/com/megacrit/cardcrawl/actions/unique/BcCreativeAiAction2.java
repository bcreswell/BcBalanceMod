//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcCreativeAiAction2 extends AbstractGameAction
{
    public BcCreativeAiAction2()
    {
        actionType = ActionType.CARD_MANIPULATION;
        duration = .5f;
        startDuration = duration;
    }
    
    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else if (duration == startDuration)
        {
            AbstractCard powerCard = createRandomPowerCard();
            BcUtility.makeCardManuallyEthereal(powerCard);
            BcUtility.addNewCardToHandOrDiscard(powerCard);
        }
        
        tickDuration();
    }
    
    AbstractCard createRandomPowerCard()
    {
        while (true)
        {
            AbstractCard powerCard = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.POWER).makeCopy();
            if ((powerCard instanceof CreativeAI) ||
                        (powerCard instanceof SelfRepair))
            {
                continue;
            }
            
            return powerCard.makeStatEquivalentCopy();
        }
    }
}
