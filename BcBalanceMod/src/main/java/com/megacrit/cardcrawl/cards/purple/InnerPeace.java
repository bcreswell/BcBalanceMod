package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.InnerPeaceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;

public class InnerPeace extends BcSkillCardBase
{
    public static final String ID = "InnerPeace";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/inner_peace";
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
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        //calm card draw
        return !upgraded ? 3 : 4;
    }
    
    int getNotCalmCardDraw()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isPlayerInStance(CalmStance.STANCE_ID),
            "#gCalm: Draw "+getCardCountString(magicNumber)+".",
            "#gElse: Enter Calm.");
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new InnerPeaceAction(magicNumber));
    }
}
