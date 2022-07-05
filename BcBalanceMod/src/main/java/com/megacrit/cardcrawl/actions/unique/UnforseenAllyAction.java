//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.*;

public class UnforseenAllyAction extends AbstractGameAction
{
    public UnforseenAllyAction()
    {
        actionType = ActionType.CARD_MANIPULATION;
        duration = .25f;
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
            AbstractCard skillCard = createRandomForeignSkillCard();
            skillCard.upgrade();
            BcUtility.makeCardManuallyEthereal(skillCard);
            BcUtility.addNewCardToHandOrDiscard(skillCard);
        }
        
        tickDuration();
    }
    
    AbstractCard createRandomForeignSkillCard()
    {
        while (true)
        {
            AbstractCard skillCard = BcUtility.getRandomCard(null, AbstractCard.CardType.SKILL, true, false,true);
            
            if ((skillCard == null) ||
                        ((skillCard.tags != null) && skillCard.hasTag(AbstractCard.CardTags.HEALING)))
            {
                continue;
            }
            
            return skillCard.makeStatEquivalentCopy();
        }
    }
}
