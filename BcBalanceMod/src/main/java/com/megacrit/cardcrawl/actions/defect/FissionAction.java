package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FissionAction extends AbstractGameAction {
    private boolean upgraded = false;

    public FissionAction(boolean upgraded) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.ENERGY;
        this.upgraded = upgraded;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            int orbCount = AbstractDungeon.player.filledOrbCount();
            addToTop(new DrawCardAction(AbstractDungeon.player, orbCount*2));
            addToTop(new GainEnergyAction(orbCount));
            if (upgraded) {
                addToTop(new EvokeAllOrbsAction());
            } else {
                addToTop(new RemoveAllOrbsAction());
            }
        }

        tickDuration();
    }
}
