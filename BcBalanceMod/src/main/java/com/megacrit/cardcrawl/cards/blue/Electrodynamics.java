package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.ElectroPower;

public class Electrodynamics extends BcPowerCardBase
{
    public static final String ID = "Electrodynamics";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/power/electrodynamics";
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
        return 2;
    }
    
    @Override
    public int getChanneledOrbCount()
    {
        return getMagicNumber();
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Lightning now hits ALL enemies. NL Channel !M! Lightning.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!player.hasPower("Electrodynamics"))
        {
            addToBot(new ApplyPowerAction(player, player, new ElectroPower(player)));
        }
        
        for (int i = 0; i < magicNumber; ++i)
        {
            AbstractOrb orb = new Lightning();
            addToBot(new ChannelAction(orb));
        }
    }
}
