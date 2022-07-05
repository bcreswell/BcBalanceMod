package com.megacrit.cardcrawl.actions.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CollectAction extends AbstractGameAction
{
    private boolean upgraded;
    
    public CollectAction(int extraAmount)
    {
        duration = Settings.ACTION_DUR_XFAST;
        actionType = AbstractGameAction.ActionType.SPECIAL;
        amount = extraAmount;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        int count = EnergyPanel.totalCount + amount;
        
        if (player.hasRelic("Chemical X"))
        {
            count += 2;
            player.getRelic("Chemical X").flash();
        }
        
        if (count > 0)
        {
            addToBot(new BcApplyPowerAction(new CollectPower(player, count)));
            player.energy.use(EnergyPanel.totalCount);
        }
        
        isDone = true;
    }
}
