package com.megacrit.cardcrawl.cards.purple;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

public class Foresight extends AbstractCard {
    public static final String ID = "Wireheading";
    private static final CardStrings cardStrings;
    
    public Foresight() {
        super("Wireheading", cardStrings.NAME, "purple/power/foresight", 1, cardStrings.DESCRIPTION, AbstractCard.CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, AbstractCard.CardTarget.NONE);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p != null) {
            this.addToBot(new VFXAction(new BorderFlashEffect(Color.VIOLET, true)));
            this.addToBot(new VFXAction(new GiantEyeEffect(p.hb.cX, p.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.8F, 1.0F, 0.0F))));
        }
        
        this.addToBot(new ApplyPowerAction(p, p, new ForesightPower(p, this.magicNumber), this.magicNumber));
    }
    
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        
    }
    
    public AbstractCard makeCopy() {
        return new Foresight();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Wireheading");
    }
}
