package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.security.acl.*;

public class HiddenShivFlingAction extends AbstractGameAction
{
    public HiddenShivFlingAction(AbstractCreature target, AbstractCreature source)
    {
        this.target = target;
        this.source = source;
        
        actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update()
    {
        int hiddenShivCount = BcUtility.getPowerAmount(HiddenShivPower.POWER_ID);
        if (BcUtility.isPlayerInCombat() && !shouldCancelAction() && (hiddenShivCount > 0))
        {
            //addToTop and reverse order so that the shiv will resolve before the next shiv starts its fling
            //this makes it so it doesn't waste shivs when the target is already dead
            
            addToTop(new ReducePowerAction(source, source, HiddenShivPower.POWER_ID, 1, true));
            
            Shiv shiv = new Shiv();
            shiv.calculateCardDamage((AbstractMonster) target);
            DamageInfo info = new DamageInfo(source, shiv.damage);
            info.base = shiv.damage;
            info.output = shiv.damage;
            info.name = HiddenShivPower.POWER_ID; //used to prevent Hidden Shivs from triggering more Hidden Shivs
            addToTop(new DamageAction(target, info, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
    
            addToTop(new VFXAction(new HiddenShivEffect(source, target), HiddenShivEffect.ShivFlyingDuration));
        }
        
        isDone = true;
    }
}
