package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
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
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        
        if (this.target != null)
        {
            addToTop(new HiddenShivDamageAction(target, AbstractDungeon.player));
            addToTop(new VFXAction(new HiddenShivEffect(source, target), HiddenShivEffect.DelayBetweenShivs));
        }
        
        isDone = true;
    }
}
