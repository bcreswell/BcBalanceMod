package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MayhemPower;

public class Mayhem extends BcPowerCardBase
{
    public static final String ID = "Mayhem";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/power/mayhem";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Mayhem";
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Start of turn: NL Play the top card of your draw pile.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(p, p, new MayhemPower(p,1), 1));
    }
}
