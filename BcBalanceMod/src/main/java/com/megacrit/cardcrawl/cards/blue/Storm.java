package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
        return !upgraded ? 1 : 0;
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
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever you play a Power card, Channel 1 Lightning.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.addToBot(new ApplyPowerAction(player, player, new StormPower(player, this.magicNumber), this.magicNumber));
    }
}