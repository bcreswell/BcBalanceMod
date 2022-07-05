package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.WaveOfTheHandPower;

public class WaveOfTheHand extends AbstractCard {
    public static final String ID = "WaveOfTheHand";
    private static final CardStrings cardStrings;
    
    public WaveOfTheHand() {
        super("WaveOfTheHand", cardStrings.NAME, "purple/skill/wave_of_the_hand", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new WaveOfTheHandPower(p, this.magicNumber), this.magicNumber));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new WaveOfTheHand();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WaveOfTheHand");
    }
}
