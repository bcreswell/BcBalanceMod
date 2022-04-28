//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Watcher extends AbstractCard
{
    public static final String ID = "Defend_P";
    private static final CardStrings cardStrings;
    
    public Defend_Watcher()
    {
        super("Defend_P", cardStrings.NAME, "purple/skill/defend", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (Settings.isDebug)
        {
            this.addToBot(new GainBlockAction(p, p, 50));
        }
        else
        {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new Defend_Watcher();
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_P");
    }
}
