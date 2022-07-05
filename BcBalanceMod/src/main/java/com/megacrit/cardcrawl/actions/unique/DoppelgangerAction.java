//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DoppelgangerAction extends AbstractGameAction
{
    boolean upgraded;
    AbstractPlayer player;
    int energyOnUse;
    
    public DoppelgangerAction(AbstractPlayer player, boolean upgraded, boolean freeToPlayOnce, int energyOnUse)
    {
        this.player = player;
        this.upgraded = upgraded;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    
    public void update()
    {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1)
        {
            effect = energyOnUse;
        }
        player.energy.use(EnergyPanel.totalCount);
        
        if (player.hasRelic(ChemicalX.ID))
        {
            effect += 2;
            player.getRelic(ChemicalX.ID).flash();
        }
        
        if (upgraded)
        {
            effect++;
        }
        
        if (effect > 0)
        {
            addToBot(new BcApplyPowerAction(new EnergizedPower(player, effect)));
            addToBot(new BcApplyPowerAction(new DrawCardNextTurnPower(player, effect)));
        }
        
        isDone = true;
    }
}
