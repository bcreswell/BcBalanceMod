//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
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
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class Warcry extends AbstractCard {
    public static final String ID = "Warcry";
    private static final CardStrings cardStrings;

    public Warcry() {
        super("Warcry", cardStrings.NAME, "red/skill/warcry", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.COMMON, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveType.ADDITIVE), 0.5F));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

    public AbstractCard makeCopy() {
        return new Warcry();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Warcry");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            cardStrings.DESCRIPTION = "Draw !M! cards. NL Put a card from your hand onto the top of your draw pile. NL Exhaust.";
            cardStrings.UPGRADE_DESCRIPTION = "Draw !M! cards. NL Put a card from your hand onto the top of your draw pile. NL Exhaust.";
        }
    }
}
