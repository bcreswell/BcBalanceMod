//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.EnvenomPowerUnblocked;

public class Envenom extends AbstractCard {
    public static final String ID = "Envenom";
    private static final CardStrings cardStrings;

    public Envenom() {
        super("Envenom", cardStrings.NAME, "green/power/envenom", 1, cardStrings.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        EnvenomPowerUnblocked envenomPower = new EnvenomPowerUnblocked(p, 1);
        if (upgraded)
        {
            envenomPower.upgrade();
        }
        this.addToBot(new ApplyPowerAction(p, p, envenomPower, 1));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Envenom();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Envenom");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Whenever an Attack deals unblocked damage, inflict 1 Poison.";
            cardStrings.UPGRADE_DESCRIPTION = "Whenever an Attack deals damage, inflict 1 Poison.";
        }
    }
}
