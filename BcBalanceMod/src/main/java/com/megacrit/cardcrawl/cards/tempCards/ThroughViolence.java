package com.megacrit.cardcrawl.cards.tempCards;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class ThroughViolence extends BcAttackCardBase
{
    public static final String ID = "ThroughViolence";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/attack/through_violence";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 20 : 30;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL When you enter Divinity, draw this card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (Settings.FAST_MODE)
        {
            addToBot(new VFXAction(new ViolentAttackEffect(monster.hb.cX, monster.hb.cY, Color.VIOLET)));
        }
        else
        {
            addToBot(new VFXAction(new ViolentAttackEffect(monster.hb.cX, monster.hb.cY, Color.VIOLET), 0.4F));
        }
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
    
    @Override
    public void onStanceChange(AbstractStance newStance)
    {
        if (newStance.ID.equals(DivinityStance.STANCE_ID))
        {
            if (AbstractDungeon.player.drawPile.contains(this))
            {
                addToBot(new MoveCardToHandAction(this, true));
            }
        }
    }
}
