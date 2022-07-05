package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class BcDarkImpulseAction extends AbstractGameAction
{
    public BcDarkImpulseAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update()
    {
        if ((this.duration == Settings.ACTION_DUR_FAST) &&
                    !AbstractDungeon.player.orbs.isEmpty())
        {
            for (AbstractOrb orb : AbstractDungeon.player.orbs)
            {
                if (orb instanceof Dark)
                {
                    addToTop(new AnimateSpecificOrbAction(orb));
                    addToTop(new TriggerOrbPassiveAction(orb));
                }
            }
            
            if (AbstractDungeon.player.hasRelic("Cables") &&
                        !(AbstractDungeon.player.orbs.get(0) instanceof EmptyOrbSlot) &&
                        AbstractDungeon.player.orbs.get(0) instanceof Dark)
            {
                addToTop(new AnimateSpecificOrbAction(AbstractDungeon.player.orbs.get(0)));
                addToTop(new TriggerOrbPassiveAction(AbstractDungeon.player.orbs.get(0)));
            }
        }
        
        this.tickDuration();
    }
}
