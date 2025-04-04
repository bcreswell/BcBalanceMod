package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        if (!upgraded)
        {
            return "Gain !M! Mantra. NL When Retained, reduce the cost by 1 until played.";
        }
        else
        {
            return "Gain !M! Mantra. NL When Retained, gain 3 Block and reduce the cost by 1 until played.";
        }
    }
    //endregion
    
    public void onRetained()
    {
        addToBot(new ReduceCostAction(this));
        if (upgraded)
        {
            addToBot(new GainBlockAction(AbstractDungeon.player, 3));
        }
    }
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        addToBot(new SetCostAction(this, getCost()));
    }
}
