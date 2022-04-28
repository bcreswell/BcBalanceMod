package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class FireBreathingPower extends AbstractPower
{
    public static final String POWER_ID = "Fire Breathing";
    private static final PowerStrings powerStrings;
    
    public FireBreathingPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "Fire Breathing";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("firebreathing");
    }
    
    public void updateDescription()
    {
        description = "Whenever you draw a #yStatus, deal #b" + amount + " damage to ALL enemies.";
    }
    
    public void onCardDraw(AbstractCard card)
    {
        if (card.type == AbstractCard.CardType.STATUS)
        {
            flash();
            addToBot(new DamageAllEnemiesAction( null, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Fire Breathing");
    }
}
