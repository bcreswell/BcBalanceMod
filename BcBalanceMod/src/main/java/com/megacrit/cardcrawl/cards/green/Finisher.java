package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Iterator;

public class Finisher extends AbstractCard {
    public static final String ID = "Finisher";
    private static final CardStrings cardStrings;
    
    public Finisher() {
        super("Finisher", cardStrings.NAME, "green/attack/finisher", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 6;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamagePerAttackPlayedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            if (c.type == AbstractCard.CardType.ATTACK) {
                ++count;
            }
        }
        
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
        }
        
        this.initializeDescription();
    }
    
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new Finisher();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Finisher");
    }
}
