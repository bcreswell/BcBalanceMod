package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class UnforeseenAlly extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("UnforseenAlly");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Unforeseen Ally";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/unforseenAlly.png";
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
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Start of Turn: NL Create a random Foreign Skill. It's temporary.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        UnforeseenAllyPower power = new UnforeseenAllyPower(player, 1);
        addToBot(new BcApplyPowerAction(power));
    }
}
