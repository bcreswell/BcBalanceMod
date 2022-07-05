package com.megacrit.cardcrawl.actions.defect;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class RandomHiddenShivAction extends AbstractGameAction
{
    public RandomHiddenShivAction(AbstractCreature source)
    {
        this.source = source;
        this.actionType = ActionType.SPECIAL;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        
        if (target != null)
        {
            addToTop(new HiddenShivFlingAction(target, player));
        }
        
        isDone = true;
    }
}
