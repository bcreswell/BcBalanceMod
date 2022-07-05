package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AccuracyPower;

public class Accuracy extends BcPowerCardBase
{
    public static final String ID = "Accuracy";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Shiv();
    }
    
    @Override
    public String getImagePath()
    {
        return "green/power/accuracy";
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
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "*Shivs and *Hidden *Shivs deal !M! additional damage.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new AccuracyPower(player, magicNumber)));
    }
}
