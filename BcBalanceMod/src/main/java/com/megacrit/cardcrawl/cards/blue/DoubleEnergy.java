package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.DoubleEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DoubleEnergy extends AbstractCard {
    public static final String ID = "Double Energy";
    private static final CardStrings cardStrings;
    
    public DoubleEnergy() {
        super("Double Energy", cardStrings.NAME, "blue/skill/double_energy", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DoubleEnergyAction());
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new DoubleEnergy();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Double Energy");
    }
}
