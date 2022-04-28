//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import java.util.Iterator;

public class Blizzard extends AbstractCard {
    public static final String ID = "Blizzard";
    private static final CardStrings cardStrings;

    public Blizzard() {
        super("Blizzard", cardStrings.NAME, "blue/attack/blizzard", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 2;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int frostCount = 0;
        Iterator var4 = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();

        while(var4.hasNext()) {
            AbstractOrb o = (AbstractOrb)var4.next();
            if (o instanceof Frost) {
                ++frostCount;
            }
        }

        if (upgraded) {
            this.baseDamage = 3 + frostCount * this.magicNumber;
        }else {
            this.baseDamage = 2 + frostCount * this.magicNumber;
        }
        this.calculateCardDamage((AbstractMonster)null);
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            this.addToBot(new VFXAction(new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }

        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AttackEffect.BLUNT_HEAVY, false));
    }

    public void applyPowers() {
        int frostCount = 0;
        Iterator var2 = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();

        while(var2.hasNext()) {
            AbstractOrb o = (AbstractOrb)var2.next();
            if (o instanceof Frost) {
                ++frostCount;
            }
        }

        if (frostCount > 0) {
            if (upgraded) {
                this.baseDamage = 3 + frostCount * this.magicNumber;
            }else {
                this.baseDamage = 2 + frostCount * this.magicNumber;
            }
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Blizzard();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blizzard");
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            //todo: figure out best practice for how to do this.
            cardStrings.DESCRIPTION = "Deal 2 damage to ALL enemies, NL and !M! more for each Frost Channeled this combat.";
            cardStrings.UPGRADE_DESCRIPTION = "Deal 3 damage to ALL enemies, NL and !M! more for each Frost Channeled this combat.";
        }
    }
}
