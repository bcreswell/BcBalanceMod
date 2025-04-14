package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.Iterator;
import java.util.UUID;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;

public class PredatorAction extends AbstractGameAction
{
    private DamageInfo info;
    private int draw;
    private int energyGain;
    
    public PredatorAction(AbstractCreature target, DamageInfo info, int draw, int energyGain)
    {
        this.info = info;
        this.setValues(target, info);
        this.draw = draw;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.energyGain = energyGain;
    }
    
    public void update()
    {
        if (duration == 0.1F && target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.SLASH_HEAVY));
            target.damage(info);
            if ((target.isDying || target.currentHealth <= 0) &&
                !target.halfDead)
            {
                //addToBot(new VFXAction(new BorderFlashEffect(Color.GREEN)));
                //on killing target
                addToBot(new DrawCardAction(draw));
                addToBot(new GainEnergyAction(energyGain));
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
            {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        
        tickDuration();
    }
}
