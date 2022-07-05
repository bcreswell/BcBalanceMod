package com.megacrit.cardcrawl.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class Expunger extends AbstractCard {
    public static final String ID = "Expunger";
    private static final CardStrings cardStrings;
    
    public Expunger() {
        super("Expunger", cardStrings.NAME, "colorless/attack/expunger", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
        baseDamage = 9;
        selfRetain = true;
    }
    
    public void setX(int amount) {
        magicNumber = amount;
        if (upgraded) {
            ++magicNumber;
        }
        
        baseMagicNumber = magicNumber;
        rawDescription = baseMagicNumber == 1 ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < magicNumber; ++i) {
            addToBot(new ExpungeVFXAction(m));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
        
    }
    
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new Expunger();
    }
    
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        card.baseMagicNumber = baseMagicNumber;
        card.magicNumber = magicNumber;
        card.description = (ArrayList)description.clone();
        return card;
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Expunger");
    }
}
