//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MulticastAction extends AbstractGameAction
{
    private boolean freeToPlayOnce = false;
    private int energyOnUse = -1;
    private int evokeCount = -1;
    private AbstractPlayer p;
    private boolean upgraded;
    
    public MulticastAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce)
    {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }
    
    public MulticastAction(int evokeCount)
    {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.evokeCount = evokeCount;
    }
    
    public void update()
    {
        if (evokeCount > 0)
        {
            for (int i = 0; i < evokeCount - 1; ++i)
            {
                addToBot(new EvokeWithoutRemovingOrbAction(1));
            }
            
            addToBot(new AnimateOrbAction(1));
            addToBot(new EvokeOrbAction(1));
        }
        
        if (!freeToPlayOnce)
        {
            AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
        }
        
        isDone = true;
    }
}