package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Skewer extends AbstractCard {
    public static final String ID = "Skewer";
    private static final CardStrings cardStrings;
    
    public Skewer() {
        super("Skewer", cardStrings.NAME, "green/attack/skewer", -1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 8;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SkewerAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }
    
    public AbstractCard makeCopy() {
        return new Skewer();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
        
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Skewer");
    }
}
