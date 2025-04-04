package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BcDevaPower;
import com.megacrit.cardcrawl.powers.watcher.DevaPower;
import com.megacrit.cardcrawl.actions.common.*;

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
        return !upgraded ? 5 : 4;
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
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When Retained, reduce this card's cost by 1. NL Start of Turn: NL Gain "+BcUtility.getEnergyString(getMagicNumber(), this)+".";
    }
    //endregion
    
    public void onRetained()
    {
        addToBot(new ReduceCostAction(this));
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new BcDevaPower(player, magicNumber)));
    }
}
