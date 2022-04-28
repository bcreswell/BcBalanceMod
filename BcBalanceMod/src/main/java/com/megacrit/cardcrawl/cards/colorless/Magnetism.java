package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MagnetismPower;

public class Magnetism extends BcPowerCardBase
{
    public static final String ID = "Magnetism";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/power/magnetism";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Magnetism";
    }
    
    @Override
    public int getMagicNumber()
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
        return "Start of turn: NL Draw 1 card randomly from your discard pile.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        MagnetismPower magnetismBuff =  new MagnetismPower(p, this.magicNumber);
        //if (upgraded)
        //{
        //    magnetismBuff.upgrade();
        //}
        
        this.addToBot(new ApplyPowerAction(p, p, magnetismBuff, this.magicNumber));
    }
}
