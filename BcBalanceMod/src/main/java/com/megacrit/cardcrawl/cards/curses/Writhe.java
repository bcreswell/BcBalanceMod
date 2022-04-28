//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.curses;

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

public class Writhe extends AbstractCard
{
    public static final String ID = "Writhe";
    private static final CardStrings cardStrings;
    
    public Writhe()
    {
        super("Writhe", cardStrings.NAME, "curse/writhe", -2, cardStrings.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.isInnate = true;
        this.isEthereal = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
    }
    
    public void upgrade()
    {
    }
    
    public AbstractCard makeCopy()
    {
        return new Writhe();
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Writhe");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Innate. NL Ethereal. NL Unplayable.";
            cardStrings.UPGRADE_DESCRIPTION = "Innate. NL Ethereal. NL Unplayable.";
        }
    }
}
