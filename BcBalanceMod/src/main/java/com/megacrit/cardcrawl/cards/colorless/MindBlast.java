package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class MindBlast extends BcAttackCardBase
{
    public static final String ID = "Mind Blast";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/attack/mind_blast";
    }

    @Override
    public String getId()
    {
        return ID;
    }

    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }

    @Override
    public boolean isAoeAttack()
    {
        return false;
    }

    @Override
    public int getDamage()
    {
        if (BcUtility.isPlayerInCombat())
        {
            return AbstractDungeon.player.drawPile.size();
        }
        else
        {
            return 0;
        }
    }
    
    @Override
    public String getBaseDescription()
    {
        String description;
        if (BcUtility.isPlayerInCombat())
        {
            description = "Deal !D! damage, equal to draw pile size.";
        }
        else
        {
            description = "Deal damage equal to draw pile size.";
        }
        
        if (upgraded)
        {
            description += " NL Draw a card.";
        }
        
        return description;
    }
    //endregion
    
    @Override
    public void applyPowers()
    {
        calculateCardDamage(null);
        super.applyPowers();
    }
    
    public void calculateCardDamage(AbstractMonster mo)
    {
        baseDamage = getDamage();
        super.calculateCardDamage(mo);
        isDamageModified = true;
        rawDescription = getFullDescription();
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal)));
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, DamageType.NORMAL), AttackEffect.NONE));
        
        if (upgraded)
        {
            addToBot(new DrawCardAction(1));
        }
    }
}