package com.megacrit.cardcrawl.powers;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.core.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.vfx.combat.BufferBlockEffect;
import com.megacrit.cardcrawl.vfx.combat.BufferParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

public class BufferPower extends BcPowerBase
{
    public static final String POWER_ID = "Buffer";
    
    protected float particleTimer;
    
    public BufferPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Buffer";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "buffer";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        int dmgThreshold = Buffer.getDamagePreventionThreshold();
        if (amount == 1)
        {
            return "Prevent the next time an enemy's attack would deal "+dmgThreshold+" or more damage.";
        }
        else
        {
            return "Prevent the next #b" + amount + " times an enemy's attack would deal "+dmgThreshold+" or more damage.";
        }
    }
    //endregion
    
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        if ((damageAmount >= Buffer.getDamagePreventionThreshold()) &&
            (info.type == DamageInfo.DamageType.NORMAL))
        {
            //give buffer animation time to play
            addToTop(new TrueWaitAction(.6f));
            addToTop(new ReducePowerAction(owner, owner, ID, 1));
            showBufferBlockParticles();
            
            return 0;
        }
        
        return damageAmount;
    }
    
    void showBufferBlockParticles()
    {
        try
        {
            if (!Settings.DISABLE_EFFECTS &&
                        (amount > 0))
            {
                for (int i = 1; i < 170; i++)
                {
                    AbstractDungeon.effectsQueue.add(new BufferBlockEffect(owner));
                }
            }
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
    
    @Override
    public void updateParticles()
    {
        try
        {
            if (!Settings.DISABLE_EFFECTS &&
                        (amount > 0) &&
                        !owner.hasPower(IntangiblePlayerPower.POWER_ID))
            {
                particleTimer -= Gdx.graphics.getDeltaTime();
                if (particleTimer < 0.0F)
                {
                    particleTimer = 0.05F;
                    
                    //only one buffer stack should look significantly different than >= 2 so that you know you're on your last one.
                    AbstractDungeon.effectsQueue.add(new BufferParticleEffect(owner));
                    
                    int max = Math.min(amount, 4);
                    for (int i = 1; i < max; i++)
                    {
                        AbstractDungeon.effectsQueue.add(new BufferParticleEffect(owner));
                        AbstractDungeon.effectsQueue.add(new BufferParticleEffect(owner));
                    }
                }
            }
        }
        catch (Exception ex)
        {
            BcUtility.log(ex.getMessage());
            throw ex;
        }
    }
}
