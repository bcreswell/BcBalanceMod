package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;

public class DmgIfPoisonedAction extends AbstractGameAction 
{
    private DamageInfo info;
    private AbstractMonster monster;

    public DmgIfPoisonedAction(AbstractMonster target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.monster = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
        this.duration = 0.01F;
    }

    public void update()
    {
        if ((monster != null) &&
            monster.hasPower(PoisonPower.POWER_ID))
        {
            if ((duration == 0.01F) &&
                (target != null) &&
                (target.currentHealth > 0))
            {
                if ((info.type != DamageType.THORNS) &&
                    info.owner.isDying)
                {
                    isDone = true;
                    return;
                }
                
                addToBot(new VFXAction(new DieDieDieEffect(monster.hb.cX, monster.hb.cY, Color.GREEN, Color.WHITE,2), 0.12F));
            }

            tickDuration();
            
            if (isDone &&
                (monster.currentHealth > 0))
            {
                
                target.damage(info);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                addToTop(new WaitAction(0.1F));
            }
        }
        else
        {
            isDone = true;
        }
    }
}