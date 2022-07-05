package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw !M! cards. NL Put a card from your hand on top of your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawCardAction(player, magicNumber));
        if (AbstractDungeon.player.hand.size() > 0)
        {
            addToBot(new PutOnDeckAction(player, player, 1, false));
        }
    }
}
