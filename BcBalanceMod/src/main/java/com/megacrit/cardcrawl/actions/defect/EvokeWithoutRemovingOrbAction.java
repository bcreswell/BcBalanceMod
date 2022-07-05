package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeWithoutRemovingOrbAction extends AbstractGameAction
{
    private int orbCount;
    
    public EvokeWithoutRemovingOrbAction(int amount)
    {
        this(amount, false);
    }
    public EvokeWithoutRemovingOrbAction(int amount, boolean slow)
    {
        if (Settings.FAST_MODE && !slow)
        {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        }
        else
        {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }
        
        this.duration = this.startDuration;
        this.orbCount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }
    
    public void update()
    {
        if (this.duration == this.startDuration)
        {
            for (int i = 0; i < this.orbCount; ++i)
            {
                AbstractDungeon.player.evokeWithoutLosingOrb();
            }
        }
        
        this.tickDuration();
    }
}
