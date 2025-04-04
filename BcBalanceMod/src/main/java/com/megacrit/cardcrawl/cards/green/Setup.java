package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.SetupAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Setup extends BcSkillCardBase
{
    public static final String ID = "Setup";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/setup";
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
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Put a card on the bottom of your draw pile. NL It costs 0 until played.";
        }
        else
        {
            return "Put a card on top of your draw pile. NL It costs 0 until played.";
        }
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new SetupAction(upgraded));
    }
}