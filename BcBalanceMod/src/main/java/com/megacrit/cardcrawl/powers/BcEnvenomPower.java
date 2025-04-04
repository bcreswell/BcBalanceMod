package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BcEnvenomPower extends AbstractPower
{
    public static final String POWER_ID = "Envenom";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    boolean isUpgraded = false;
    
    public BcEnvenomPower(AbstractCreature owner, int newAmount)
    {
        this.name = NAME;
        this.ID = "Envenom";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("envenom");
    }
    
//    public void upgrade()
//    {
//        isUpgraded = true;
//        this.name = NAME + "+";
//        this.ID = "Envenom+";
//        this.updateDescription();
//    }
    
    public void updateDescription()
    {
        description = "Whenever an Attack deals unblocked damage, Inflict " + amount + " Poison.";
        //description = "Whenever an Attack deals damage, apply " + amount + " Poison.";
//        if (isUpgraded)
//        {
//            this.description = "Whenever an Attack deals damage, apply " + amount + " Poison.";
//        }
//        else
//        {
//            this.description = "Whenever an Attack deals unblocked damage, apply " + amount + " Poison.";
//        }
    }
    
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
//        if ((target != this.owner) &&
//            (info.type == DamageType.NORMAL))
//        {
        if ((damageAmount > 0) &&
            (target != this.owner) &&
            (info.type == DamageType.NORMAL))
        {
            flash();
            PoisonPower poison = new PoisonPower(target, owner, amount);
            poison.isQuiet = true;
            addToTop(new BcApplyPowerAction(target, AbstractDungeon.player, poison, AbstractGameAction.AttackEffect.NONE, true));
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Envenom");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
