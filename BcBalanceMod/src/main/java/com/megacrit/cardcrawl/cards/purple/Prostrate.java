package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

public class Prostrate extends BcSkillCardBase
{
    public static final String ID = "Prostrate";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/prostrate";
    }
    
    @Override
    public void onInitialized()
    {
        cardsToPreview = new Insight();
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 0)
        {
            return "Gain !B! Block. NL Shuffle an Insight into your draw pile.";
        }
        else
        {
            return "Gain !B! Block. NL Gain !M! Mantra. NL Shuffle an Insight into your draw pile.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, block));
        
        if (magicNumber > 0)
        {
            addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        }
        
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
    }
}
