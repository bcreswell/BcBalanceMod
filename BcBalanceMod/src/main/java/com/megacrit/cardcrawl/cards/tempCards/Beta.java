package com.megacrit.cardcrawl.cards.tempCards;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Beta extends BcSkillCardBase
{
    public static final String ID = "Beta";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Omega();
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/skill/beta";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Shuffle an *Omega into your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
    }
}
