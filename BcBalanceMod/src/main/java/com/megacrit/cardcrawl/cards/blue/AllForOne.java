package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.AllCostToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllForOne extends AbstractCard {
    public static final String ID = "All For One";
    private static final CardStrings cardStrings;
    
    public AllForOne() {
        super("All For One", cardStrings.NAME, "blue/attack/all_for_one", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.BLUE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 10;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new AllCostToHandAction(0));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeDamage(5);
            this.upgradeName();
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new AllForOne();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("All For One");
    }
}
