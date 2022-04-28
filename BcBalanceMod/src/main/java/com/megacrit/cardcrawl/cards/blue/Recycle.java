//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.defect.RecycleAction;
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

public class Recycle extends AbstractCard {
    public static final String ID = "Recycle";
    private static final CardStrings cardStrings;

    public Recycle() {
        super("Recycle", cardStrings.NAME, "blue/skill/recycle", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        RecycleAction recycle = new RecycleAction();
        if (upgraded)
        {
            recycle.upgrade();
        }
        this.addToBot(recycle);
    }

    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Recycle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Recycle");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Exhaust a card. NL Gain [B] equal to NL its cost - 1. NL X-Cost: gain your current [B] - 1.";
            cardStrings.UPGRADE_DESCRIPTION = "Exhaust a card. NL Gain [B] equal to NL its cost. NL X-Cost: gain your current [B].";
        }
    }
}
