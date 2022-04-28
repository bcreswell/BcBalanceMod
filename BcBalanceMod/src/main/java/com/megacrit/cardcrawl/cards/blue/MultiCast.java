//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.unique.MulticastAction;
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

public class MultiCast extends AbstractCard
{
    public static final String ID = "Multi-Cast";
    private static final CardStrings cardStrings;
    
    public MultiCast()
    {
        super("Multi-Cast", cardStrings.NAME, "blue/skill/multicast", -1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.NONE);
        this.showEvokeValue = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new MulticastAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
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
    
    public AbstractCard makeCopy()
    {
        return new MultiCast();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Multi-Cast");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Evoke your next orb multiple times. NL Lightning or Frost: NL      X+1 times. NL Dark or Plasma: NL    X times.";
            cardStrings.UPGRADE_DESCRIPTION = "Evoke your next orb multiple times. NL Lightning or Frost: NL    X+2 times. NL Dark or Plasma: NL    X+1 times.";
        }
    }
}