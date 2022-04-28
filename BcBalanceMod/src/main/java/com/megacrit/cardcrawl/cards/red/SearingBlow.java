package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

public class SearingBlow extends AbstractCard
{
    public static final String ID = "Searing Blow";
    private static final CardStrings cardStrings;
    
    public SearingBlow()
    {
        this(0);
    }
    
    public SearingBlow(int upgrades)
    {
        super("Searing Blow", cardStrings.NAME, "red/attack/searing_blow", 2, cardStrings.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 16;
        this.timesUpgraded = upgrades;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m != null)
        {
            this.addToBot(new VFXAction(new SearingBlowEffect(m.hb.cX, m.hb.cY, this.timesUpgraded), 0.2F));
        }
        
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    
    public void upgrade()
    {
        this.upgradeDamage(4 + this.timesUpgraded);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }
    
    public boolean canUpgrade()
    {
        return true;
    }
    
    public AbstractCard makeCopy()
    {
        return new SearingBlow(this.timesUpgraded);
    }
    
    static
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Searing Blow");
    }
}
