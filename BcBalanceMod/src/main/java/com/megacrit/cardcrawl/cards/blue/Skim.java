package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Skim extends BcSkillCardBase
{
    public static final String ID = "Skim";
    
    //region Description
    @Override
    public String getImagePath()
    {
        return "blue/skill/skim";
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
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isFocusZero(),
            "Draw !M! cards. NL #g0 #gFocus: Draw 1 more.");
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int drawCount = magicNumber;
        if (BcUtility.getCurrentFocus() == 0)
        {
            drawCount++;
        }
        
        addToBot(new DrawCardAction(player, drawCount));
    }
}
