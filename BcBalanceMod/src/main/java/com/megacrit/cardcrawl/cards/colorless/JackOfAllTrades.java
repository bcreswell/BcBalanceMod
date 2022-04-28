//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.colorless;

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

public class JackOfAllTrades extends AbstractCard
{
    public static final String ID = "Jack Of All Trades";
    private static final CardStrings cardStrings;
    
    public JackOfAllTrades()
    {
        super("Jack Of All Trades", cardStrings.NAME, "colorless/skill/jack_of_all_trades", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
            this.addToBot(new MakeTempCardInHandAction(c, 1));
            if (this.upgraded)
            {
                c.upgrade();
            }
        }
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new JackOfAllTrades();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Jack Of All Trades");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Create !M! random colorless cards. NL Exhaust.";
            cardStrings.UPGRADE_DESCRIPTION = "Create !M! random upgraded colorless cards. NL Exhaust.";
        }
    }
}
