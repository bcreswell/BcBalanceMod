//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
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

public class AllOutAttack extends AbstractCard
{
    public static final String ID = "All Out Attack";
    private static final CardStrings cardStrings;

    public AllOutAttack()
    {
        super("All Out Attack", cardStrings.NAME, "green/attack/all_out_attack", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 10;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.SLASH_HEAVY));
        if (!upgraded)
        {
            this.addToBot(new DiscardAction(p, p, 1, true));
        }
        else
        {
            this.addToBot(new DiscardAction(p, p, 1, false));
        }
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            this.upgradeDamage(3);

            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new AllOutAttack();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("All Out Attack");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            cardStrings.DESCRIPTION = "Deal !D! damage to ALL enemies. NL Discard 1 card at random.";
            cardStrings.UPGRADE_DESCRIPTION = "Deal !D! damage to ALL enemies. NL Discard 1 card.";
        }
    }
}
