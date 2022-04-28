package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;

public class NaturalViolentFire extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("NaturalViolentFire");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "    Natural, Violent Fire";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/naturalViolentFire.png";
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
        return "If you're in Wrath at the start of the turn, NL Gain [W] .";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(player, player, new NaturalViolentFirePower(player, magicNumber), magicNumber));
    }
}
