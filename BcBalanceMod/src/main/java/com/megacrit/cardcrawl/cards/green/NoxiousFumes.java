package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;

public class NoxiousFumes extends BcPowerCardBase
{
    public static final String ID = "Noxious Fumes";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/power/noxious_fumes";
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
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "At the start of your turn, apply !M! Poison to ALL enemies.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new NoxiousFumesPower(player, magicNumber)));
    }
}
