package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GoodInstincts extends AbstractCard {
    public static final String ID = "Good Instincts";
    private static final CardStrings cardStrings;

    public GoodInstincts() {
        super("Good Instincts", cardStrings.NAME, "colorless/skill/good_instincts", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    public AbstractCard makeCopy() {
        return new GoodInstincts();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Good Instincts");
    }
}
