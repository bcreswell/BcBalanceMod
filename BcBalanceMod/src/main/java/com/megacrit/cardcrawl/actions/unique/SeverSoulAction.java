//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.brashmonkey.spriter.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.Iterator;

public class SeverSoulAction extends AbstractGameAction
{
    private DamageInfo info;
    private float startingDuration;
    
    public SeverSoulAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        actionType = ActionType.WAIT;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
    }
    
    public void update()
    {
        if (duration == startingDuration)
        {
            int attackCount = 0;
            for (AbstractCard card : AbstractDungeon.player.hand.group)
            {
                if ((card.type == CardType.SKILL) || (card.type == CardType.POWER) || (card.type == CardType.STATUS) || (card.type == CardType.CURSE))
                {
                    addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    
                    attackCount++;
                }
            }
            
            for (int i = 0; i < attackCount; i++)
            {
                AbstractMonster randomTarget = BcUtility.getRandomTarget();
                if (randomTarget != null)
                {
                    addToBot(new DamageAction(randomTarget, info, AttackEffect.LIGHTNING));
                }
            }
            
            isDone = true;
        }
    }
}
