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
    private AbstractPlayer p;
    private int energyOnUse = -1;
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
    
    public void update()
    {
        if (AbstractDungeon.player.hasOrb())
        {
            int evokeCount = EnergyPanel.totalCount;
            if (this.energyOnUse != -1)
            {
                evokeCount = this.energyOnUse;
            }
            
            if (this.p.hasRelic("Chemical X"))
            {
                evokeCount += 2;
                this.p.getRelic("Chemical X").flash();
            }
            
            if (this.upgraded)
            {
                ++evokeCount;
            }
            
            AbstractOrb orbToEvoke = AbstractDungeon.player.orbs.get(0);
            if ((orbToEvoke instanceof Frost) || (orbToEvoke instanceof Lightning))
            {
                evokeCount++;
            }
            
            if (evokeCount > 0)
            {
                for (int i = 0; i < evokeCount - 1; ++i)
                {
                    this.addToBot(new EvokeWithoutRemovingOrbAction(1));
                }
                
                this.addToBot(new AnimateOrbAction(1));
                this.addToBot(new EvokeOrbAction(1));
                if (!this.freeToPlayOnce)
                {
                    this.p.energy.use(EnergyPanel.totalCount);
                }
            }
        }
        
        this.isDone = true;
    }
}