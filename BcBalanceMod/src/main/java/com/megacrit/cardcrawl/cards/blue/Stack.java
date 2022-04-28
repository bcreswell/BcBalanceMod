package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Stack extends BcSkillCardBase
{
    public static final String ID = "Stack";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/stack";
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
    
    public int getExtraBlock()
    {
        return !upgraded ? 2 : 5;
    }
    
    public int getBlock()
    {
        if (BcUtility.isPlayerInCombat())
        {
            return AbstractDungeon.player.discardPile.size() + getExtraBlock();
        }
        else
        {
            return getExtraBlock();
        }
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain Block equal to the number of cards in your discard pile +" + getExtraBlock() + ".";
    }
    //endregion
    
    @Override
    public String getTemporaryExtraDescription()
    {
        return "( !B! block.)";
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
    }
    
    public void applyPowers()
    {
        baseBlock = getBlock();
        
        super.applyPowers();
    }
}
