package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.DevaPower;

public class DevaForm extends BcPowerCardBase
{
    public static final String ID = "DevaForm";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/deva_form";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getEthereal()
    {
        return !upgraded;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "At the start of your turn, gain [W] and increase this gain by !M!.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        this.addToBot(new ApplyPowerAction(player, player, new DevaPower(player), 1));
    }
}
