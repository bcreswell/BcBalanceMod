//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AutoShields extends AbstractCard
{
    public static final String ID = "Auto Shields";
    private static final CardStrings cardStrings;
    
    public AutoShields()
    {
        super("Auto Shields", cardStrings.NAME, "blue/skill/auto_shields", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 11;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (AbstractDungeon.player.currentBlock == 0)
        {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(5);
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new AutoShields();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Auto Shields");
    }
}