package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class ImprovisedPoison extends  BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImprovisedPoison");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Improvised Poison";
    }
    
    @Override
    public CardColor getCardColor()
    {
        return CardColor.COLORLESS;
    }
    
    @Override
    public String getImagePath()
    {
        return "green/improvisedPoison.png";
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
        return -2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict !M! Poison.";
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
            addToBot(new ApplyPowerAction(JustImprovising.TempTarget, player, new PoisonPower(JustImprovising.TempTarget, player, magicNumber), magicNumber));
        }
        JustImprovising.TempTarget = null;
    }
}
