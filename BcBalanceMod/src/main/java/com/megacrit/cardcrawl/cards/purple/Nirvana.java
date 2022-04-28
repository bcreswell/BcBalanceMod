package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.NirvanaPower;

public class Nirvana extends AbstractCard
{
    public static final String ID = "Nirvana";
    private static final CardStrings cardStrings;
    
    public Nirvana()
    {
        super("Nirvana", cardStrings.NAME, "purple/power/nirvana", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new ApplyPowerAction(p, p, new NirvanaPower(p, this.magicNumber), this.magicNumber));
    }
    
    public AbstractCard makeCopy()
    {
        return new Nirvana();
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
        
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Nirvana");
    }
}
