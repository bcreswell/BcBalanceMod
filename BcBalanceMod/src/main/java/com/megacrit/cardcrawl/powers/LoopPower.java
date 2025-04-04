package com.megacrit.cardcrawl.powers;

import bcBalanceMod.BcUtility;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.vfx.combat.LoopEffect;

public class LoopPower extends BcPowerBase
{
    public static final String POWER_ID = "Loop";
    
    protected float particleTimer = 1.5f;
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Loop";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "loop";
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
        if (amount == 1)
        {
            return "End of Turn: NL Trigger the passive on your next orb. NL (Start of Turn for Plasma Orbs)";
        }
        else
        {
            return "End of Turn: NL Trigger the passive on your next orb #b"+amount+" times. NL (Start of Turn for Plasma Orbs)";
        }
    }
    //endregion
    
    public LoopPower(AbstractCreature owner, int amt)
    {
        super(owner, amt);
    }
    
    public void atStartOfTurn()
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
            if (orb instanceof Plasma)
            {
                flash();
                
                for (int i = 0; i < amount; ++i)
                {
                    orb.onStartOfTurn();
                    orb.onEndOfTurn();
                }
            }
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
            if (!(orb instanceof Plasma))
            {
                flash();
                
                for (int i = 0; i < amount; ++i)
                {
                    orb.onStartOfTurn();
                    orb.onEndOfTurn();
                }
            }
        }
    }
    
    @Override
    public void updateParticles()
    {
        try
        {
            if (!Settings.DISABLE_EFFECTS &&
                (amount > 0))
            {
                particleTimer -= Gdx.graphics.getDeltaTime();
                if (particleTimer < 0f)
                {
                    particleTimer = 4f + (amount * .3f);
                    
                    for (int i = 0; i < amount; i++)
                    {
                        AbstractDungeon.effectsQueue.add(new LoopEffect(i * .3f));
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