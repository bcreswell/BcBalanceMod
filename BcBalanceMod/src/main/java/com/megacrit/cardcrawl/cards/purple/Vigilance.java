//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Vigilance extends AbstractCard {
    public static final String ID = "Vigilance";
    private static final CardStrings cardStrings;

    public Vigilance() {
        super("Vigilance", cardStrings.NAME, "purple/skill/vigilance", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 10;
        this.block = this.baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new ChangeStanceAction("Calm"));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(6);
        }

    }

    public AbstractCard makeCopy() {
        return new Vigilance();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Vigilance");
    }
}