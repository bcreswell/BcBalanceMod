package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeeingRed extends AbstractCard {
    public static final String ID = "Seeing Red";
    private static final CardStrings cardStrings;
    
    public SeeingRed() {
        super("Seeing Red", cardStrings.NAME, "red/skill/seeing_red", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.NONE);
        this.exhaust = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainEnergyAction(2));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new SeeingRed();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Seeing Red");
    }
}
