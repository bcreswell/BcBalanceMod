package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.*;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Anomaly extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Anomaly");
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Anomaly";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/anomaly.png";
    }
    
    @Override
    public int getCost()
    {
        return upgraded ? 1 : 2;
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Channel a random orb. NL Gain !M! Focus.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        addToBot(new ApplyPowerAction(player, player, new FocusPower(player, this.magicNumber), this.magicNumber));
    }
}
