//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
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
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Coolheaded extends AbstractCard {
    public static final String ID = "Coolheaded";
    private static final CardStrings cardStrings;

    public Coolheaded() {
        super("Coolheaded", cardStrings.NAME, "blue/skill/coolheaded", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ChannelAction(new Frost()));
        this.addToBot(new DrawCardAction(p, this.magicNumber));

        if (BcUtility.getCurrentFocus() == 0)
        {
            this.addToBot(new DrawCardAction(p, 1));
        }
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void triggerOnGlowCheck()
    {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (BcUtility.getCurrentFocus() == 0)
        {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public AbstractCard makeCopy() {
        return new Coolheaded();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Coolheaded");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Channel 1 Frost. NL Draw !M! card. NL If you have zero Focus, NL draw 1 more.";
            cardStrings.UPGRADE_DESCRIPTION = "Channel 1 Frost. NL Draw !M! cards. NL If you have zero Focus, NL draw 1 more.";
        }
    }
}
