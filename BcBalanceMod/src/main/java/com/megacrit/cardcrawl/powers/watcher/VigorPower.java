package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class VigorPower extends AbstractPower
{
    public static final String POWER_ID = "Vigor";
    private static final PowerStrings powerStrings;
    
    public VigorPower(AbstractCreature owner, int amount)
    {
        this.name = powerStrings.NAME;
        this.ID = "Vigor";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("vigor");
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
    }
    
    public void updateDescription()
    {
        description = "Your next Attack deals #b" + amount + " additional damage.";
    }
    
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (type == DamageInfo.DamageType.NORMAL)
        {
            return damage + (float) this.amount;
        }
        
        return damage;
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vigor");
    }
}
