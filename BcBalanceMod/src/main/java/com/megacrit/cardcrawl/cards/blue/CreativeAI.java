//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CreativeAIPower;

public class CreativeAI extends AbstractCard
{
    public static final String ID = "Creative AI";
    private static final CardStrings cardStrings;
    
    public CreativeAI()
    {
        super("Creative AI", cardStrings.NAME, "blue/power/creative_ai", 3, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        CreativeAIPower ai = new CreativeAIPower(p, 1);
//        if (upgraded)
//        {
//            ai.upgrade();
//        }
        
        this.addToBot(new ApplyPowerAction(p, p, ai, 1));
    }
    
    public AbstractCard makeCopy()
    {
        return new CreativeAI();
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Creative AI");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Ethereal. NL Start of turn: NL Choose 1 of 3 Powers to create. NL (Can't create itself or Self Repair.)";
            cardStrings.UPGRADE_DESCRIPTION = "Start of turn: NL Choose 1 of 3 Powers to create. NL (Can't create itself or Self Repair.)";
        }
    }
}
