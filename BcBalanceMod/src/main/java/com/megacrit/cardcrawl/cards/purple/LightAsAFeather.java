package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.watcher.*;

public class LightAsAFeather extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("LightAsAFeather");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Light as a Feather";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/lightAsAFeather.png";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 0)
        {
            return "Deal !D! damage.";
        }
        else
        {
            return "Deal !D! damage. NL Gain !M! Mantra.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        
        if (magicNumber > 0)
        {
            addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        }
    }
}
