package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MalaiseAction extends AbstractGameAction
{
    private boolean freeToPlayOnce = false;
    private boolean upgraded = false;
    private AbstractPlayer player;
    private AbstractMonster monster;
    private int energyOnUse = -1;
    
    public MalaiseAction(AbstractPlayer player, AbstractMonster monster, boolean upgraded, boolean freeToPlayOnce, int energyOnUse)
    {
        this.player = player;
        this.monster = monster;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    
    public void update()
    {
        int strengthDownEffect = EnergyPanel.totalCount;
        if (energyOnUse != -1)
        {
            strengthDownEffect = this.energyOnUse;
        }
        
        if (player.hasRelic("Chemical X"))
        {
            strengthDownEffect += 2;
            player.getRelic("Chemical X").flash();
        }
        
        if (upgraded)
        {
            strengthDownEffect++;
        }
        
        int weakEffect = strengthDownEffect + 1;
        
        if (strengthDownEffect > 0)
        {
            addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -strengthDownEffect), -strengthDownEffect));
        }
        
        if (weakEffect > 0)
        {
            addToBot(new ApplyPowerAction(monster, player, new WeakPower(monster, weakEffect, false), weakEffect));
        }
        
        if (!freeToPlayOnce)
        {
            player.energy.use(EnergyPanel.totalCount);
        }
        
        isDone = true;
    }
}
