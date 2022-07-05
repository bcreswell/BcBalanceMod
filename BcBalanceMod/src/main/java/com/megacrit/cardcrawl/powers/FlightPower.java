package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlightPower extends AbstractPower
{
    public static final String POWER_ID = "Flight";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int storedAmount;
    
    public FlightPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "Flight";
        this.owner = owner;
        this.amount = amount;
        storedAmount = amount;
        updateDescription();
        loadRegion("flight");
        priority = 50;
    }
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05F);
    }
    
    public void updateDescription()
    {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
    
    public void atStartOfTurn()
    {
        amount = storedAmount;
        updateDescription();
    }
    
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type)
    {
        return calculateDamageTakenAmount(damage, type);
    }
    
    private float calculateDamageTakenAmount(float damage, DamageInfo.DamageType type)
    {
        return type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS ? damage / 2.0F : damage;
    }
    
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        Boolean willLive = calculateDamageTakenAmount((float) damageAmount, info.type) < (float) owner.currentHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0 && willLive)
        {
            flash();
            addToBot(new ReducePowerAction(owner, owner, "Flight", 1));
        }
        
        return damageAmount;
    }
    
    public void onRemove()
    {
        addToBot(new ChangeStateAction((AbstractMonster) owner, "GROUNDED"));
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flight");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
