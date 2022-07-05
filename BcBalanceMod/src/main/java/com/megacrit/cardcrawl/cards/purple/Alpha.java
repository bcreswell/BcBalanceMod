package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Beta;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Alpha extends BcSkillCardBase
{
    public static final String ID = "Alpha";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/alpha";
    }
    
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Beta();
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
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Shuffle a *Beta into your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
    }
}
