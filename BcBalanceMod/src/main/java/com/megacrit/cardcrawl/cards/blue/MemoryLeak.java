//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class MemoryLeak extends CustomCard
{
    public static final String ID = BcBalanceMod.makeID("MemoryLeak");
    private static final CardStrings cardStrings;
    
    public MemoryLeak()
    {
        super(
            ID,
            cardStrings.NAME,
            makeCardPath("blue/memoryLeak.png"),
            2,
            cardStrings.DESCRIPTION,
            CardType.ATTACK,
            CardColor.BLUE,
            CardRarity.COMMON,
            CardTarget.ENEMY);
    
        this.baseDamage = 10;
        this.showEvokeValue = true;
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new AnimateOrbAction(1));
        this.addToBot(new EvokeOrbAction(1));
        if (p.maxOrbs < 10)
        {
            this.addToBot(new IncreaseMaxOrbAction(1));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }
    
//    @Override
//    public void onInitialized()
//    {
//        this.showEvokeValue = true;
//    }
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeDamage(6);
            this.upgradeBaseCost(1);
            
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static
    {
        cardStrings = new CardStrings();
        cardStrings.NAME = "Memory Leak";
        cardStrings.DESCRIPTION = "Evoke your next orb. NL Gain an orb slot. NL Deal !D! damage.";
        cardStrings.UPGRADE_DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
