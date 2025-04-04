package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.*;

public class Nirvana extends BcPowerCardBase
{
    public static final String ID = "Nirvana";
    public static final int BlockPerInsight = 2;
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/nirvana";
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
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1 )
        {
            return "When you Scry or gain Mantra, also gain that amount of Block.";
        }
        else
        {
            return "When you Scry or gain Mantra, also gain that amount of Block x!M!.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new BcApplyPowerAction(new NirvanaPower(player, magicNumber)));
    }
}
