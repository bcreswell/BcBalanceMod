package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EstablishmentPower;

public class Establishment extends BcPowerCardBase
{
    public static final String ID = "Establishment";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/establishment";
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
        return CardRarity.RARE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public boolean getInnate() {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 1)
        {
            return "End of turn: NL Reduce the cost by 1 of a random Retained card for the rest of combat.";
        }
        else
        {
            return "End of turn: NL Reduce the cost by 1 of !M! random Retained cards for the rest of combat.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new EstablishmentPower(player, magicNumber)));
    }
}
