package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.*;

public class TemporalParadoxPower extends BcPowerBase
{
    public static final String POWER_ID = "TemporalParadoxPower";
    public int HpTarget;
    
    public TemporalParadoxPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
        
        HpTarget = owner.currentHealth;
        updateDescription();
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Temporal Paradox";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.SeparateApplications;
    }
    
    @Override
    public String getImagePath()
    {
        return "time";
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
            return "Return to " + HpTarget + " health in " + amount + " turn.";
        }
        else
        {
            return "Return to " + HpTarget + " health in " + amount + " turns.";
        }
    }
    //endregion
    
    public void atStartOfTurnPostDraw()
    {
        amount--;
        
        if (amount <= 0)
        {
            if (!owner.isDying)
            {
                owner.currentHealth = HpTarget;
                if (owner.currentHealth > owner.maxHealth)
                {
                    owner.currentHealth = owner.maxHealth;
                }
                
                if (((float) owner.currentHealth > (float) owner.maxHealth / 2.0F)
                            && owner.isBloodied)
                {
                    owner.isBloodied = false;
                    for (AbstractRelic relic : AbstractDungeon.player.relics)
                    {
                        relic.onNotBloodied();
                    }
                }
                
                owner.healthBarUpdatedEvent();
    
                CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
                AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            }
            
            addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
        }
        
        updateDescription();
    }
}
