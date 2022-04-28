//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DoppelgangerAction extends AbstractGameAction
{
    private boolean freeToPlayOnce;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;
    
    public DoppelgangerAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse)
    {
        this.p = p;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    
    public void update()
    {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
        {
            effect = this.energyOnUse;
        }
        
        if (this.p.hasRelic("Chemical X"))
        {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        
        if (this.upgraded)
        {
            effect++;
        }
        
        if (effect > 0)
        {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new EnergizedPower(this.p, effect), effect));
        }
        
        //one extra card draw when not upgraded
        if (!upgraded)
        {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new DrawCardNextTurnPower(this.p, effect + 1), effect + 1));
        }
        else if (effect > 0)
        {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new DrawCardNextTurnPower(this.p, effect), effect));
        }
        
        if (!this.freeToPlayOnce)
        {
            this.p.energy.use(EnergyPanel.totalCount);
        }
        
        this.isDone = true;
    }
}
