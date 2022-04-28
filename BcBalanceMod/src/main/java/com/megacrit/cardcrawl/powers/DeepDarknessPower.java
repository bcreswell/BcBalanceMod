//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.defect.AnimateSpecificOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.TriggerOrbPassiveAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Dark;

public class DeepDarknessPower extends AbstractPower
{
    private boolean isUpgraded;
    
    public DeepDarknessPower(AbstractCreature owner, boolean isUpgraded)
    {
        this.ID = "DeepDarknessPower" + (isUpgraded ? "+" : "");
        this.name = "The Deep Darkness" + (isUpgraded ? "+" : "");
        this.owner = owner;
        this.isUpgraded = isUpgraded;
        this.amount = 1;
        this.updateDescription();
        this.loadRegion("darkembrace");
    }
    
    public void updateDescription()
    {
        if (isUpgraded)
        {
            this.description = "End of turn: NL If you have an empty orb slot, NL Channel 1 Dark, NL and trigger its passive.";
        }
        else
        {
            this.description = "End of turn: NL If you have an empty orb slot, NL Channel 1 Dark.";
        }
    }
    
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer)
        {
            AbstractPlayer player = AbstractDungeon.player;
    
            for (int i = 0; i < amount; i++)
            {
                if (player.hasEmptyOrb())
                {
                    Dark darkOrb = new Dark();
                    addToBot(new ChannelAction(darkOrb));
            
                    if (isUpgraded)
                    {
                        addToBot(new TriggerOrbPassiveAction(darkOrb));
                        addToBot(new AnimateSpecificOrbAction(darkOrb));
                    }
                }
            }
        }
    }
}
