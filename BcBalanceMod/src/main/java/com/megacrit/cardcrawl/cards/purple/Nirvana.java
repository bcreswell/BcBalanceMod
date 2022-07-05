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
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When you Scry or gain Mantra, also gain Block for the same amount. NL NL When play an *Insight or *Miracle, gain 1 Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new BcApplyPowerAction(new NirvanaPower(player, 1)));
    }
}
