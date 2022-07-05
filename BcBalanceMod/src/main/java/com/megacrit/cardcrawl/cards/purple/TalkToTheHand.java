package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BlockReturnPower;

public class TalkToTheHand extends AbstractCard {
    public static final String ID = "TalkToTheHand";
    private static final CardStrings cardStrings;
    
    public TalkToTheHand() {
        super("TalkToTheHand", cardStrings.NAME, "purple/attack/talk_to_the_hand", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.PURPLE, CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(m, p, new BlockReturnPower(m, this.magicNumber), this.magicNumber));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new TalkToTheHand();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("TalkToTheHand");
    }
}
