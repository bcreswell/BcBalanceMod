//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

public class Rainbow extends AbstractCard {
    public static final String ID = "Rainbow";
    private static final CardStrings cardStrings;

    public Rainbow() {
        super("Rainbow", cardStrings.NAME, "blue/skill/rainbow", 2, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 3;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new RainbowCardEffect()));
        this.addToBot(new ChannelAction(new Lightning()));
        this.addToBot(new ChannelAction(new Frost()));
        this.addToBot(new ChannelAction(new Dark()));
        if (upgraded)
        {
            this.addToBot(new ChannelAction(new Plasma()));
        }
    }

    public AbstractCard makeCopy() {
        return new Rainbow();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //.exhaust = false;
            this.showEvokeOrbCount = 4;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rainbow");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Channel 1 Lightning, NL 1 Frost and 1 Dark. NL Exhaust.";
            cardStrings.UPGRADE_DESCRIPTION = "Channel 1 Lightning, NL 1 Frost, 1 Dark NL and 1 Plasma. NL Exhaust.";
        }
    }
}
