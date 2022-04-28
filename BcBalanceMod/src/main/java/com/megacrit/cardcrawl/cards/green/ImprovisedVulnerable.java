package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class ImprovisedVulnerable extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImprovisedVulnerable");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Improvised Vulnerable";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/improvisedVulnerable.png";
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
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return JustImprovising.getImprovisedVulnerable(upgraded);
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict !M! Vulnerable.";
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
            //adding to top instead so that a hidden shiv triggered by this will be buffed
            addToTop(new ApplyPowerAction(JustImprovising.TempTarget, player, new VulnerablePower(JustImprovising.TempTarget, magicNumber, false), magicNumber));
        }
        JustImprovising.TempTarget = null;
    }
}
