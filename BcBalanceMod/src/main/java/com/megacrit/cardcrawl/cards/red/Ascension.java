//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Ascension extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("Ascension");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Ascension";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/ascension.png";
    }
    
    @Override
    public int getCost()
    {
        return 3;
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        //str per card
        return 1;
    }
    
    int getInitialHeal()
    {
        return !upgraded ? 0 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        String desc = "When you lose HP from a card, gain !M! Strength. NL When you play an Attack, heal for !M!.";
        
        int initialHeal = getInitialHeal();
        if (initialHeal > 0)
        {
            desc = "Heal for " + initialHeal + ". NL " + desc;
        }
        
        return desc;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        if (upgraded)
        {
            addToBot(new HealAction(player, player, getInitialHeal()));
        }
        
        addToBot(new BcApplyPowerAction(new AscensionPower(player, magicNumber)));
    }
}
