package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

public class MasterReality extends BcPowerCardBase
{
    public static final String ID = "MasterReality";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/master_reality";
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever a card is created during combat, Upgrade it. NL NL If it was already upgraded, reduce its cost by 1 instead.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new MasterRealityPower(player)));
    }
}
