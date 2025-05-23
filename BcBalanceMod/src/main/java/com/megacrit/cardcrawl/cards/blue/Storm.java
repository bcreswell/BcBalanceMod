package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StormPower;

public class Storm extends BcPowerCardBase
{
    public static final String ID = "Storm";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/power/storm";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public boolean getInnate()
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
        return CardRarity.RARE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever you play a Power card, Channel !M! Lightning.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new StormPower(player, magicNumber)));
    }
}
