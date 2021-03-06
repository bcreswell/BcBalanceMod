package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TrueGrit extends BcSkillCardBase
{
    public static final String ID = "True Grit";
    public static final String NothingFootnote = "Does nothing if it NL lacks a target card.";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/true_grit";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getBlock()
    {
        return 9;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Exhaust a random card. NL Gain !B! Block.";
        }
        else
        {
            return "Exhaust a card. NL Gain !B! Block.";
        }
    }
    
    @Override
    public String getFootnote()
    {
        return TrueGrit.NothingFootnote;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //does nothing if there's no target to exhaust
        if (player.hand.size() >= 2)
        {
            if (upgraded)
            {
                addToBot(new ExhaustAction(1, false));
            }
            else
            {
                addToBot(new ExhaustAction(1, true, false, false));
            }
            
            addToBot(new GainBlockAction(player, player, block));
        }
    }
}
