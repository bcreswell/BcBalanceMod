package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class Tempest extends BcSkillCardBase
{
    public static final String ID = "Tempest";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/tempest";
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        int lightningToChannel = EnergyPanel.totalCount + 1;
        if (upgraded)
        {
            lightningToChannel++;
        }
        
        if ((AbstractDungeon.player != null) && AbstractDungeon.player.hasRelic(ChemicalX.ID))
        {
            lightningToChannel += 2;
        }
        
        return lightningToChannel;
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
    public int getCost()
    {
        return -1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Channel X+1 Lightning.";
        }
        else
        {
            return "Channel X+2 Lightning.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int lightningToChannel = getOrbCountToChannel();
        
        if (lightningToChannel > 0)
        {
            for (int i = 0; i < lightningToChannel; ++i)
            {
                addToBot(new ChannelAction(new Lightning()));
            }
            
            if (!freeToPlayOnce)
            {
                player.energy.use(EnergyPanel.totalCount);
            }
        }
    }
}
