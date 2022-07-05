package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class TantrumAction extends AbstractGameAction
{
    AbstractCard card;
    int baseDmg;
    
    public TantrumAction(AbstractCard card, AbstractCreature source, AbstractCreature target, int baseDmg, int attackTimes)
    {
        setValues(target, source, attackTimes);
        this.card = card;
        this.baseDmg = baseDmg;
    }
    
    public void update()
    {
        if (target != null)
        {
            card.calculateCardDamage((AbstractMonster) target);
    
            for (int i = 0; i < amount; i++)
            {
                addToBot(new DamageAction(target, new DamageInfo(source, card.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
        
        isDone = true;
    }
}
