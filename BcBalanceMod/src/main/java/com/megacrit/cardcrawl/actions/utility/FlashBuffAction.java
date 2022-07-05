//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

public class FlashBuffAction extends AbstractGameAction
{
    AbstractPower power;
    
    public FlashBuffAction(AbstractPower power)
    {
        this.power = power;
        actionType = ActionType.WAIT;
    }
    
    public void update()
    {
        power.flash();
        isDone = true;
    }
}
