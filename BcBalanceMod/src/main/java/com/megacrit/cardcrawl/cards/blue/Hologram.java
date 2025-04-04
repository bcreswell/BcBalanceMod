//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hologram extends BcSkillCardBase
{
    public static final String ID = "Hologram";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Hologram";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/hologram";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() > 0)
        {
            return "Pick a card to Retrieve that costs !M! or more.";
        }
        else
        {
            return "Pick a card to Retrieve.";
        }
    }
    
    @Override
    public String getFootnote()
    {
        return "Can't Retrieve Hologram.";
    }
    
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RetrieveAction(1, false, magicNumber, Hologram.ID));
    }
}
