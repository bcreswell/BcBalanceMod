//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class RedoAction extends AbstractGameAction
{
    private AbstractOrb orb;
    private boolean isUpgraded;

    public RedoAction()
    {
        this.isUpgraded = false;
        this.actionType = ActionType.DAMAGE;
    }
    public RedoAction(boolean isUpgraded)
    {
        this.isUpgraded = isUpgraded;
        this.actionType = ActionType.DAMAGE;
    }

    public void update()
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            this.orb = (AbstractOrb)AbstractDungeon.player.orbs.get(0);
            if (this.orb instanceof EmptyOrbSlot)
            {
                this.isDone = true;
            }
            else
            {
                if (isUpgraded)
                {
                    addToTop(new AnimateSpecificOrbAction(this.orb));
                    this.addToTop(new TriggerOrbPassiveAction(this.orb));
                }
                this.addToTop(new ChannelAction(this.orb, false));
                this.addToTop(new EvokeOrbAction(1));
            }
        }

        this.isDone = true;
    }
}
