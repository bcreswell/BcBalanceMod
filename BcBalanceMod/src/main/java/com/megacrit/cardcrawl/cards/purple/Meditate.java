//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.RetrieveAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.MeditateAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Meditate extends BcSkillCardBase
{
    public static final String ID = "Meditate";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/meditate";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 0 : 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 0)
        {
            return "Enter Calm. NL End your turn.";
        }
        else
        {
            return "Retrieve "+getCardCountString(magicNumber)+" of your choice and Retain it. NL Enter Calm. NL End your turn.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (magicNumber > 0)
        {
            addToBot(new RetrieveAction(magicNumber, true, 0, null));
        }
        
        addToBot(new ChangeStanceAction("Calm"));
        BcUtility.EndTurnEarly();
    }
}
