package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FireBreathingPower;

public class FireBreathing extends BcPowerCardBase
{
    public static final String ID = "Fire Breathing";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/power/fire_breathing";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
        return !upgraded ? 6 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever you draw a Status card, deal !M! damage to ALL enemies.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new FireBreathingPower(player, magicNumber), magicNumber));
    }
}
