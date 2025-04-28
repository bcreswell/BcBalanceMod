package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.Iterator;

public class Impatience extends AbstractCard {
    public static final String ID = "Impatience";
    private static final CardStrings cardStrings;

    public Impatience() {
        super("Impatience", cardStrings.NAME, "colorless/skill/impatience", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ConditionalDrawAction(this.magicNumber, CardType.ATTACK));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.shouldGlow() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private boolean shouldGlow() {
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            c = (AbstractCard)var1.next();
        } while(c.type != CardType.ATTACK);

        return false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }

    }

    public AbstractCard makeCopy() {
        return new Impatience();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Impatience");
    }
}
