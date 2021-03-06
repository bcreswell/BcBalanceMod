//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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

public class JackOfAllTrades extends BcSkillCardBase
{
    public static final String ID = "Jack Of All Trades";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/jack_of_all_trades";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Create !M! random colorless cards.";
        }
        else
        {
            return "Create !M! random upgraded colorless cards.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            AbstractCard card = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
            if (upgraded)
            {
                card.upgrade();
            }
            addToBot(new MakeTempCardInHandAction(card, 1));
        }
    }
}
