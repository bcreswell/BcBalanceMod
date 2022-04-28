package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Protect extends AbstractCard {
    public static final String ID = "Protect";
    private static final CardStrings cardStrings;
    
    public Protect() {
        super("Protect", cardStrings.NAME, "purple/skill/protect", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.baseBlock = 12;
        this.selfRetain = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
    }
    
    public AbstractCard makeCopy() {
        return new Protect();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(6);
        }
        
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Protect");
    }
}
