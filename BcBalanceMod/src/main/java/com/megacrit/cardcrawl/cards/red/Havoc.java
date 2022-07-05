//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Havoc extends AbstractCard {
    public static final String ID = "Havoc";
    private static final CardStrings cardStrings;

    public Havoc() {
        super("Havoc", cardStrings.NAME, "red/skill/havoc", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.NONE);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), true));
//        if (this.upgraded)
//        {
//            this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng), true));
//        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            exhaust = false;
            //this.upgradeBaseCost(0);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Havoc();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Havoc");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            cardStrings.DESCRIPTION = "Play the top card of your draw pile and Exhaust it. NL Exhaust.";
            cardStrings.UPGRADE_DESCRIPTION = "Play the top card of your draw pile and Exhaust it.";
        }
    }
}
