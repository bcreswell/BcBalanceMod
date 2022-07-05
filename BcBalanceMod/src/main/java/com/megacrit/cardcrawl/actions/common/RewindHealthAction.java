package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.*;

public class RewindHealthAction extends AbstractGameAction
{
    public AbstractCreature owner;
    public int hpTarget;
    
    public RewindHealthAction(AbstractCreature owner, int amount)
    {
        this.owner = owner;
        hpTarget = amount;
    }
    
    @Override
    public void update()
    {
        if (!owner.isDying)
        {
            owner.currentHealth = hpTarget;
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
        }
        
        isDone = true;
    }
}
