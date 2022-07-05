package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EchoPower;

public class EchoForm extends BcPowerCardBase
{
    public static final String ID = "Echo Form";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/power/echo_form";
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
        return !upgraded ? 3 : 2;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "The first card you play each turn is played twice.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new EchoPower(player, 1)));
    }
}
