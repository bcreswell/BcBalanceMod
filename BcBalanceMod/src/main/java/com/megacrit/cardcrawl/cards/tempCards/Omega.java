package com.megacrit.cardcrawl.cards.tempCards;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.OmegaPower;

public class Omega extends BcPowerCardBase
{
    public static final String ID = "Omega";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/power/omega";
    }
    
    @Override
    public int getCost()
    {
        return 3;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
        
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 50 : 60;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "At the end of your turn, deal !M! damage to ALL enemies.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new OmegaPower(player, magicNumber)));
    }
}
