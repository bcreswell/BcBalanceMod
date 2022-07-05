package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;

public class DarkOrbEvokeAction extends AbstractGameAction
{
    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private boolean muteSfx = false;
    
    public DarkOrbEvokeAction(DamageInfo info, AbstractGameAction.AttackEffect effect)
    {
        AbstractMonster targetMonster = null;
        
        //dark orbs are attracted to lockon first and weakest enemy second.
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
        {
            if (!monster.isDeadOrEscaped())
            {
                if (targetMonster == null)
                {
                    targetMonster = monster;
                }
                else
                {
                    boolean monsterLockon = monster.hasPower(LockOnPower.POWER_ID);
                    boolean targetLockon = targetMonster.hasPower(LockOnPower.POWER_ID);
                    
                    if (monsterLockon == targetLockon)
                    {
                        if (monster.currentHealth < targetMonster.currentHealth)
                        {
                            targetMonster = monster;
                        }
                    }
                    else if (monsterLockon && !targetLockon)
                    {
                        targetMonster = monster;
                    }
                }
            }
        }
        
        this.info = info;
        setValues(targetMonster, info);
        actionType = AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
        duration = 0.1F;
    }
    
    public void update()
    {
        if ((!shouldCancelAction() || info.type == DamageInfo.DamageType.THORNS) && target != null)
        {
            if (duration == 0.1F)
            {
                info.output = Dark.applyLockOn(target, info.base);
                if (info.type != DamageInfo.DamageType.THORNS && (info.owner.isDying || info.owner.halfDead))
                {
                    isDone = true;
                    return;
                }
                
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, muteSfx));
            }
            
            tickDuration();
            if (isDone)
            {
                if (attackEffect == AbstractGameAction.AttackEffect.POISON)
                {
                    target.tint.color = Color.CHARTREUSE.cpy();
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                else if (attackEffect == AbstractGameAction.AttackEffect.FIRE)
                {
                    target.tint.color = Color.RED.cpy();
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                else if (attackEffect == AttackEffect.DARK)
                {
                    target.tint.color = Color.PURPLE.cpy();
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                
                target.damage(info);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
                
                if (!Settings.FAST_MODE)
                {
                    addToTop(new WaitAction(0.1F));
                }
            }
        }
        else
        {
            isDone = true;
        }
    }
}
