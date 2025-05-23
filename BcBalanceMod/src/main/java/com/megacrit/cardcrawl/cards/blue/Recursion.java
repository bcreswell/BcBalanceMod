package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Recursion extends BcSkillCardBase
{
    public static final String ID = "Redo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/recursion";
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
    public int getBlock()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 1;
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return Math.min(AbstractDungeon.player.filledOrbCount(), 1);
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Evoke your next orb. Channel it again.";
        if (getBlock() > 0)
        {
            description += " NL Gain !B! Block.";
        }

        return description;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RecursionAction(1, 0,true));

        if (block > 0)
        {
            addToBot(new GainBlockAction(player, player, block));
        }
    }
}
