package com.megacrit.cardcrawl.actions.defect;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Iterator;

public class LightningOrbEvokeAction extends AbstractGameAction
{
    private DamageInfo info;
    private boolean hitAll;
    
    public LightningOrbEvokeAction(DamageInfo info, boolean hitAll)
    {
        this.info = info;
        actionType = AbstractGameAction.ActionType.DAMAGE;
        attackEffect = AbstractGameAction.AttackEffect.NONE;
        this.hitAll = hitAll;
    }
    
    public void update()
    {
        if (!hitAll)
        {
            AbstractCreature m = AbstractDungeon.getRandomMonster();
            if (m != null)
            {
                float speedTime = 0.1F;
                if (!AbstractDungeon.player.orbs.isEmpty())
                {
                    speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
                }
                
                if (Settings.FAST_MODE)
                {
                    speedTime = 0.0F;
                }
                
                info.output = AbstractOrb.applyLockOn(m, info.base);
                addToTop(new DamageAction(m, info, AbstractGameAction.AttackEffect.NONE, true));
                addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        }
        else
        {
            float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();
            if (Settings.FAST_MODE)
            {
                speedTime = 0.0F;
            }
            
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, Lightning.createDamageMatrix(info.base), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            Iterator var5 = AbstractDungeon.getMonsters().monsters.iterator();
            
            while (var5.hasNext())
            {
                AbstractMonster m3 = (AbstractMonster) var5.next();
                if (!m3.isDeadOrEscaped() && !m3.halfDead)
                {
                    addToTop(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), speedTime));
                }
            }
            
            addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        }
        
        isDone = true;
    }
}
