package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.*;

public class RemovePowerIfEmptyAction extends AbstractGameAction
{
    String powerId;
    boolean isMuted;
    
    //this is used w/ DizzyPower where we need to decrement the amount immediately
    // but can't remove the power immediately when amount reaches zero because of
    // rules against modifying a collection while iterating over it.
    
    public RemovePowerIfEmptyAction(AbstractCreature target, String powerId, boolean mute)
    {
        this.target = target;
        source = target;
        this.powerId = powerId;
        isMuted = mute;
        duration = 0;
    }
    public RemovePowerIfEmptyAction(AbstractCreature target, String powerId)
    {
        this(target, powerId, false);
    }
    
    @Override
    public void update()
    {
        if (target.isDeadOrEscaped())
        {
            isDone = true;
            return;
        }
        
        AbstractPower powerToRemove = target.getPower(powerId);
        
        if ((powerToRemove != null) &&
                    (powerToRemove.amount <= 0))
        {
            if (!isMuted)
            {
                AbstractDungeon.effectList.add(
                        new PowerExpireTextEffect(
                                target.hb.cX - target.animX,
                                target.hb.cY + target.hb.height / 2.0F,
                                powerToRemove.name,
                                powerToRemove.region128));
            }
            
            powerToRemove.onRemove();
            target.powers.remove(powerToRemove);
            AbstractDungeon.onModifyPower();
            
            if (AbstractDungeon.player.orbs != null)
            {
                for (AbstractOrb orb : AbstractDungeon.player.orbs)
                {
                    orb.updateDescription();
                }
            }
        }
        
        isDone = true;
    }
}
