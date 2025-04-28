package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class JustLuckyAction extends AbstractGameAction
{
    AbstractCard card;
    
    public JustLuckyAction(AbstractCreature target, AbstractCreature source, AbstractCard card)
    {
        this.target = target;
        this.source = source;
        this.card = card;
    }
    
    @Override
    public void update()
    {
        //50% crit chance
        if (AbstractDungeon.cardRng.random(0, 1) == 1)
        {
            AbstractCard tempCard = card.makeStatEquivalentCopy();
            tempCard.calculateCardDamage((AbstractMonster) target);
            tempCard.damage = tempCard.damage * 3;
            
            //slo-mo coin when it crits
            addToBot(new VFXAction(new FlickCoinEffect(source.hb.cX, source.hb.cY, target.hb.cX, target.hb.cY, 2f), 2F));
            addToBot(new ShakeScreenAction(0.0F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
            
            addToBot(new VFXAction(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.FIRE, false)));
            //addToBot(new VFXAction(new BorderLongFlashEffect(Color.GOLD)));
            addToBot(new DamageAction(target, new DamageInfo(source, tempCard.damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
        }
        else
        {
            addToBot(new VFXAction(new FlickCoinEffect(source.hb.cX, source.hb.cY, target.hb.cX, target.hb.cY, 0.5F), 0.5F));
            addToBot(new DamageAction(target, new DamageInfo(source, card.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        }
        
        isDone = true;
    }
}
