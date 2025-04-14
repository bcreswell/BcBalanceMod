package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.unique.BcRetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

public class ThinkingAhead extends BcSkillCardBase
{
    public static final String ID = "Thinking Ahead";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/thinking_ahead";
    }
    
    @Override
    public CardColor getCardColor()
    {
        return CardColor.GREEN;
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
    public int getMagicNumber()
    {
        //card draw
        return 2;
    }
    
    public int getRetainCount()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw "+ getCardCountString(magicNumber)+". NL Pick "+ getCardCountString(getRetainCount())+" to Retain. NL NL Draw 1 less card next turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawCardAction(player, magicNumber));
        
        int retainableCards = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group)
        {
            if (!card.selfRetain && !card.retain && !card.isEthereal)
            {
                retainableCards++;
            }
        }

        if ((retainableCards > 0) &&
            !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
            !AbstractDungeon.player.hasPower("Equilibrium"))
        {
            addToBot(new BcRetainCardsAction(player, getRetainCount()));
        }
        
        addToBot(new BcApplyPowerAction(new DrawReductionPower(player, 1)));
    }
}