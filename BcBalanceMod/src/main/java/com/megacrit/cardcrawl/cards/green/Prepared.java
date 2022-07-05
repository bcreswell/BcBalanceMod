//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Prepared extends AbstractCard
{
    public static final String ID = "Prepared";
    private static final CardStrings cardStrings;
    
    public Prepared()
    {
        super("Prepared", cardStrings.NAME, "green/skill/prepared", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        if (upgraded)
        {
            this.addToBot(new DiscardAction(p, p, this.magicNumber, false));
        }
        else
        {
            this.addToBot(new DiscardAction(p, p, this.magicNumber, false));
        }
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new Prepared();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Prepared");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            cardStrings.DESCRIPTION = "Retain. NL Draw !M! card. NL Discard !M! card.";
            cardStrings.UPGRADE_DESCRIPTION = "Retain. NL Draw !M! cards. NL Discard !M! cards.";
        }
    }
}
