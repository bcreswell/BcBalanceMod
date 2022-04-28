package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class BladeOfLegendAction extends AbstractGameAction
{
    private AbstractCard card;
    private AbstractGameAction.AttackEffect effect;
    
    public BladeOfLegendAction(AbstractCard card, AbstractGameAction.AttackEffect effect)
    {
        this.card = card;
        this.effect = effect;
    }
    
    public BladeOfLegendAction(AbstractCard card)
    {
        this(card, AbstractGameAction.AttackEffect.NONE);
    }
    
    public void update()
    {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        
        if (target != null)
        {
            card.calculateCardDamage((AbstractMonster) target);
            
            if (AbstractGameAction.AttackEffect.LIGHTNING == effect)
            {
                addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                addToTop(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
            }
            else
            {
//                addToTop(new BcApplyPowerAction(target, new VulnerablePower(target, 1, false)));
//                addToTop(new BcApplyPowerAction(target, new WeakPower(target, 1, false)));
                addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), effect));
            }
        }
        
        isDone = true;
    }
}
