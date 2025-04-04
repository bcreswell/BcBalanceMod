package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.FissionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Plasma;

public class Fission extends BcSkillCardBase
{
    public static final String ID = "Fission";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/fission";
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
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Remove all your Orbs. NL Gain [B] and draw !M! cards for each Orb removed.";
        }
        else
        {
            return "Remove all your Orbs. NL Gain [B] and draw !M! cards for each Orb removed. NL Channel a Plasma.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new FissionAction(false));
        
        if (upgraded)
        {
            addToBot(new ChannelAction(new Plasma()));
        }
    }
}