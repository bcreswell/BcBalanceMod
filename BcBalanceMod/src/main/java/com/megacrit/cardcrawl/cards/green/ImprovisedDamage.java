package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class ImprovisedDamage extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImprovisedDamage");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Improvised Damage";
    }
    
    @Override
    public CardColor getCardColor()
    {
        return CardColor.COLORLESS;
    }
    
    @Override
    public String getImagePath()
    {
        return "green/improvisedDamage.png";
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public AbstractCard.CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 6 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster notUsed)
    {
        onChoseThisOption();
    }
    
    public void onChoseThisOption()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if ((JustImprovising.TempTarget != null) && (player != null))
        {
            calculateCardDamage(JustImprovising.TempTarget);
            addToBot(new DamageAction(JustImprovising.TempTarget, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        JustImprovising.TempTarget = null;
    }
}
