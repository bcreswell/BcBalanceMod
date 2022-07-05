package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TemporalParadoxPower extends BcPowerBase
{
    public static final String POWER_ID = "TemporalParadoxPower";
    public int hpTarget;
    
    public TemporalParadoxPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
        
        hpTarget = owner.currentHealth;
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
            return "Return to " + hpTarget + " health in " + amount + " turn.";
        }
        else
        {
            return "Return to " + hpTarget + " health in " + amount + " turns.";
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
                CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
                AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
                AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                
                addToBot(new TrueWaitAction(.3f));
                addToBot(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                addToBot(new RewindHealthAction(owner, hpTarget));
            }
            
            addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
        }
        
        updateDescription();
    }
}
