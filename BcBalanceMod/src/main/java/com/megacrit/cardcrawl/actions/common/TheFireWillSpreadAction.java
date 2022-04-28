package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class TheFireWillSpreadAction extends AbstractGameAction
{
    public TheFireWillSpreadAction(int damageAmount)
    {
        amount = damageAmount;
    }
    
    public void update()
    {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null)
        {
            addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            addToTop(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
        }
        
        isDone = true;
    }
}
