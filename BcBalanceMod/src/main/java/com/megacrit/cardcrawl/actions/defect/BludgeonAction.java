package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BludgeonAction extends AbstractGameAction
{
    private int energyGainAmt;
    private int drawAmt;
    private DamageInfo info;
    
    public BludgeonAction(AbstractCreature target, DamageInfo info, int energyAmt, int drawAmt)
    {
        this.info = info;
        setValues(target, info);
        this.energyGainAmt = energyAmt;
        this.drawAmt = drawAmt;
        actionType = AbstractGameAction.ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_FASTER;
    }
    
    public void update()
    {
        if (duration == Settings.ACTION_DUR_FASTER && target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            
            target.damage(info);
            if (target.isDying || target.currentHealth <= 0)
            {
                addToBot(new GainEnergyAction(energyGainAmt));
                addToBot(new DrawCardAction(drawAmt));
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        
        tickDuration();
    }
}
