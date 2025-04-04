package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.EscapePlanAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SideStep extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("SideStep");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Side Step";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/sideStep.png";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 6;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw a card. NL If you draw a Skill, gain !B! Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawCardAction(1, new EscapePlanAction(block)));
    }
}
