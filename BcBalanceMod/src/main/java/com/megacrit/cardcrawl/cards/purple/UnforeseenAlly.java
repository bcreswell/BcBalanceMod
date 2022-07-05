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
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Start of turn: NL Create a random upgraded foreign Skill. NL It will become Ethereal.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new UnforeseenAllyPower(player, 1)));
    }
}
