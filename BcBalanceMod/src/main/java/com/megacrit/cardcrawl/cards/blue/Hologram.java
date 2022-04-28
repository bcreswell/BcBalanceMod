//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hologram extends AbstractCard
{
    public static final String ID = "Hologram";
    private static final CardStrings cardStrings;
    
    public Hologram()
    {
        super("Hologram", cardStrings.NAME, "blue/skill/hologram", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF);
        this.baseBlock = 5;
        this.exhaust = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new BetterDiscardPileToHandAction(1));
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeBlock(2);
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
        
    }
    
    public AbstractCard makeCopy()
    {
        return new Hologram();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hologram");
    }
}
