package com.megacrit.cardcrawl.cards.status;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Dazed extends BcStatusCardBase
{
    public static final String ID = "Dazed";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "status/dazed";
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    //endregion
    
    public void triggerOnExhaust()
    {
        if (upgraded)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            addToBot(new GainEnergyAction(-1));
        }
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    }
}
