package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Evaluate extends BcSkillCardBase
{
    public static final String ID = "Evaluate";
    
    //region card parameters
    @Override
    public void onInitialized()
    {
        cardsToPreview = new Insight();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/evaluate";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 7 : 11;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Shuffle an *Insight into your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new GainBlockAction(player, player, block));
        
        AbstractCard card = new Insight();
        addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true, false));
    }
}
