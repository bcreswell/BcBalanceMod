package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RupturePower;

public class Rupture extends AbstractCard {
    public static final String ID = "Rupture";
    private static final CardStrings cardStrings;
    
    public Rupture() {
        super("Rupture", cardStrings.NAME, "red/power/rupture", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RupturePower(p, this.magicNumber), this.magicNumber));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new Rupture();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rupture");
    }
}
