//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
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

public class RipAndTear extends AbstractCard
{
    public static final String ID = "Rip and Tear";
    private static final CardStrings cardStrings;
    
    public RipAndTear()
    {
        super("Rip and Tear", cardStrings.NAME, "blue/attack/rip_and_tear", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < this.magicNumber; ++i)
        {
            this.addToBot(new NewRipAndTearAction(this));
        }
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new RipAndTear();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rip and Tear");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Deal !D! damage to a random enemy !M! times.";
            cardStrings.UPGRADE_DESCRIPTION = cardStrings.DESCRIPTION;
        }
    }
}