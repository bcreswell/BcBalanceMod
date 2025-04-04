package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Berserk extends BcPowerCardBase
{
    public static final String ID = "Berserk";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Berserk";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/power/berserk";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Suffer !M! Vulnerable. NL At the start of your turn, gain [R].";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, magicNumber, false)));
        addToBot(new BcApplyPowerAction(new BerserkPower(player, 1)));
    }
}
