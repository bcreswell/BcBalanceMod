//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.actions.watcher.EmptyBlahAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Violence extends BcSkillCardBase
{
    public static final String ID = "Violence";

    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Violence";
    }

    @Override
    public String getImagePath()
    {
        return "colorless/skill/violence";
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
        return CardRarity.RARE;
    }

    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }

    @Override
    public int getMagicNumber()
    {
        return 3;
    }

    @Override
    public String getBaseDescription()
    {
        return "Draw !M! Attack cards.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawPileToHandAction(magicNumber, CardType.ATTACK));
    }
}
