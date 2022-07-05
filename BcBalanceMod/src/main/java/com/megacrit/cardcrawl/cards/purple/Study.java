package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.StudyPower;

public class Study extends BcPowerCardBase
{
    public static final String ID = "Study";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/power/study";
    }
    
    @Override
    protected void onInitialized()
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "End of turn: NL Shuffle an *Insight into your draw pile.";
        }
        else
        {
            return "End of turn: NL Shuffle !M! *Insights into your draw pile.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new StudyPower(player, magicNumber)));
    }
}
