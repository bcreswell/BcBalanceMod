package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

public class HauntedWhispers extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("HauntedWhispers");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Haunted Whispers";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/hauntedWhispers.png";
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public boolean getExhaust()
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
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Pick an Ethereal card from your draw pile and put it in your hand.";
        }
        else
        {
            return "Pick an Ethereal card from your draw or exhaust piles and put it in your hand.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new HauntedWhispersAction(magicNumber, upgraded));
    }
}
