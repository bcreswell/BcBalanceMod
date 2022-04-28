package com.megacrit.cardcrawl.actions.defect;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

public class NewRipAndTearAction extends AttackDamageRandomEnemyAction
{
    public NewRipAndTearAction(AbstractCard card)
    {
        super(card);
    }
    
    public void update()
    {
        super.update();
    
        addToTop(new WaitAction(0.1F));
        
        if (target != null)
        {
            addToTop(new VFXAction(new RipAndTearEffect(target.hb.cX, target.hb.cY, Color.RED, Color.GOLD)));
        }
    }
}
