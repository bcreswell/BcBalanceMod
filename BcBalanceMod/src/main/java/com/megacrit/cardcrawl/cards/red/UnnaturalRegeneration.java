package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class UnnaturalRegeneration extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("UnnaturalRegeneration");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "       Unnatural Regeneration";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/unnaturalRegeneration.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
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
    public String getBaseDescription()
    {
        return "When you lose HP from a card, heal back that amount at the end of combat.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new UnnaturalRegenerationPower(player, 1)));
    }
}
