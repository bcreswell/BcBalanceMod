package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Scrawl extends BcSkillCardBase
{
    public static final String ID = "Scrawl";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/scrawl";
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
        return 1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw cards until your hand is full.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ExpertiseAction(player, 10));
    }
}
