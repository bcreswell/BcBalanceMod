package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class AllInGoodTime extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("AllInGoodTime");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "All In Good Time";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/allInGoodTime.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 4 : 3;
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
        return 5;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! Mantra. NL When Retained, lower this card's cost by 1.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        this.addToBot(new ApplyPowerAction(player, player, new MantraPower(player, magicNumber), magicNumber));
    }
    
    public void onRetained()
    {
        this.addToBot(new ReduceCostAction(this));
    }

//    public void initializeDescription()
//    {
//        super.initializeDescription();
//        this.keywords.add(GameDictionary.ENLIGHTENMENT.NAMES[0].toLowerCase());
//    }
}
