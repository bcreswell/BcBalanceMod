package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ForceField extends AbstractCard
{
    public static final String ID = "Force Field";
    private static final CardStrings cardStrings;
    
    public ForceField()
    {
        super("Force Field", cardStrings.NAME, "blue/skill/forcefield", 4, cardStrings.DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.BLUE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        baseBlock = 15;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null)
        {
            configureCostsOnNewCard();
        }
    }
    
    public void configureCostsOnNewCard()
    {
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();
        
        while (var1.hasNext())
        {
            AbstractCard c = (AbstractCard) var1.next();
            if (c.type == AbstractCard.CardType.POWER)
            {
                updateCost(-1);
            }
        }
    }
    
    public void triggerOnCardPlayed(AbstractCard c)
    {
        if (c.type == AbstractCard.CardType.POWER)
        {
            updateCost(-1);
        }
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new GainBlockAction(p, p, block));
    }
    
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBlock(5);
        }
    }
    
    public AbstractCard makeCopy()
    {
        return new ForceField();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Force Field");
    }
}
