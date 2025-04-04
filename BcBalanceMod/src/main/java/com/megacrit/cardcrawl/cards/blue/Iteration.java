package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.BcAttackCardBase;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.RecursionAction;

public class Iteration extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Iteration");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Iteration";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/iteration.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Rotate your orbs clockwise.";
        if (upgraded)
        {
            description += " NL Create a temporary *Iteration.";
        }
        
        return description;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RecursionAction(1, 0, false));
        
        if (upgraded)
        {
            Iteration card = new Iteration();
            BcUtility.makeCardTemporary(card);
            BcUtility.addNewCardToHandOrDiscard(card);
        }
    }
}