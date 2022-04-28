package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Berserk extends AbstractCard
{
    public static final String ID = "Berserk";
    private static final CardStrings cardStrings;
    
    public Berserk()
    {
        super("Berserk", cardStrings.NAME, "red/power/berserk", 0, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, magicNumber, false)));
        addToBot(new BcApplyPowerAction(new BerserkPower(player, 1)));
    }
    
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(-1);
        }
        
    }
    
    public AbstractCard makeCopy()
    {
        return new Berserk();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Berserk");
    }
}
