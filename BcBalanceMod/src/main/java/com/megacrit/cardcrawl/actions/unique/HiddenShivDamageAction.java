package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.security.acl.*;

public class HiddenShivDamageAction extends AbstractGameAction
{
    private DamageInfo info;
    private boolean damageDone = false;
    Shiv shiv = new Shiv();
    
    public HiddenShivDamageAction(AbstractCreature target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        
        info = new DamageInfo(source, shiv.baseDamage);
        info.name = HiddenShivPower.POWER_ID; //used to prevent Hidden Shivs from triggering more Hidden Shivs
        
        this.amount = info.base;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        this.duration = .3F;
    }
    
    @Override
    public void update()
    {
        if (isDone)
        {
            return;
        }
        
        if (!damageDone && BcUtility.isPlayerInCombat())  //first update
        {
            AbstractPower hiddenShivsPower = source.getPower(HiddenShivPower.POWER_ID);
            if (this.shouldCancelAction() || (hiddenShivsPower == null) || (hiddenShivsPower.amount <= 0))
            {
                isDone = true;
            }
            else
            {
                damageDone = true;
                isDone = true;
                
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, false));
                
                if (this.attackEffect == AbstractGameAction.AttackEffect.POISON)
                {
                    target.tint.color.set(Color.CHARTREUSE.cpy());
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE)
                {
                    target.tint.color.set(Color.RED);
                    target.tint.changeColor(Color.WHITE.cpy());
                }
    
                shiv.calculateCardDamage((AbstractMonster)target);
                info.base = shiv.damage;
                info.output = shiv.damage;
                target.damage(info);
                
                hiddenShivsPower.amount--;
                if (hiddenShivsPower.amount <= 0)
                {
                    addToBot(new RemoveSpecificPowerAction(source, source, HiddenShivPower.POWER_ID));
                }
                
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
        }
        else
        {
            tickDuration();
        }
    }
}
