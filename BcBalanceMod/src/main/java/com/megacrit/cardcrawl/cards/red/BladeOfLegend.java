package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

public class BladeOfLegend extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("BladeOfLegend");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Blade of Legend";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/bladeOfLegend.png";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 11 : 15;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ALL_ENEMY;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to random enemies 3 times.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer var1, AbstractMonster var2)
    {
        //attack 1
        addToBot(new TrueWaitAction(.2f));
        addToBot(new BladeOfLegendAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        
        //attack 2
        addToBot(new TrueWaitAction(.2f));
        addToBot(new BladeOfLegendAction(this, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        
        //attack 3
        addToBot(new TrueWaitAction(.2f));
        addToBot(new BladeOfLegendAction(this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }
}
