package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Consume extends BcSkillCardBase
{
    public static final String ID = "Consume";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Consume";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/consume";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Lose 1 Orb slot to NL Gain !M! Focus.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (getCurrentOrbSlotCount() > 0)
        {
            addToBot(new DecreaseMaxOrbAction(1));
            addToBot(new BcApplyPowerAction(new FocusPower(player, magicNumber)));
        }
    }
}
