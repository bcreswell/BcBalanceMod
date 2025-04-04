package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.watcher.WrathNextTurnPower;
import com.megacrit.cardcrawl.stances.*;

public class SimmeringFury extends BcSkillCardBase
{
    public static final String ID = "SimmeringFury";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Simmering Fury";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/simmering_fury";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isPlayerInStance(WrathStance.STANCE_ID),
            "#gWrath: Draw "+BcUtility.getCardCountString(magicNumber)+".",
            "#gElse: Next turn, enter Wrath and Draw "+BcUtility.getCardCountString(magicNumber)+".");
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            addToBot(new DrawCardAction(magicNumber));
        }
        else
        {
            addToBot(new BcApplyPowerAction(new WrathNextTurnPower(player)));
            addToBot(new BcApplyPowerAction(new DrawCardNextTurnPower(player, magicNumber)));
        }
    }
}
