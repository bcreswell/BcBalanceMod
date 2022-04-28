package com.megacrit.cardcrawl.actions.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CollectAction extends AbstractGameAction
{
    private boolean freeToPlayOnce = false;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;
    
    public CollectAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded)
    {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }
    
    public void update()
    {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
        {
            effect = this.energyOnUse + 1;
        }
        
        if (this.p.hasRelic("Chemical X"))
        {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        
        if (this.upgraded && BcUtility.playerHasPower(MasterRealityPower.POWER_ID))
        {
            effect++;
        }
        
        if (effect > 0)
        {
            this.addToBot(new ApplyPowerAction(this.p, this.p, new CollectPower(this.p, effect), effect));
            if (!this.freeToPlayOnce)
            {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        
        this.isDone = true;
    }
}
