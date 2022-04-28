//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class StormOfSteelAction extends AbstractGameAction
{
    public StormOfSteelAction(AbstractCreature source)
    {
        this.source = source;
        duration = this.startDuration = 0;
        actionType = ActionType.SPECIAL;
    }
    
    public void update()
    {
        if (!isDone)
        {
            isDone = true;
            
            AbstractPlayer player = AbstractDungeon.player;
            AbstractPower hiddenShivPower = player.getPower(HiddenShivPower.POWER_ID);
            
            if ((hiddenShivPower != null) && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                //launch all the remaining Hidden Shivs at random targets
                for (int i = 0; i < hiddenShivPower.amount; i++)
                {
                    addToBot(new RandomHiddenShivAction(source));
                }
            }
        }
    }
}
