//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.unique.SetupAction;
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

public class Setup extends AbstractCard {
    public static final String ID = "Setup";
    private static final CardStrings cardStrings;

    public Setup()
    {
        super("Setup", cardStrings.NAME, "green/skill/setup", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.NONE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SetupAction());
    }

    public AbstractCard makeCopy() {
        return new Setup();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeBaseCost(0);
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Setup");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            cardStrings.DESCRIPTION = "Put a card from your hand on top of your draw pile. NL It costs 0 until played. Exhaust.";
            cardStrings.UPGRADE_DESCRIPTION = "Put a card from your hand on top of your draw pile. NL It costs 0 until played.";
        }
    }
}
