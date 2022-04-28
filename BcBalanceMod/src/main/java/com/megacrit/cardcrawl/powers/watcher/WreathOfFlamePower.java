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

public class WreathOfFlamePower extends AbstractPower
{
    public static final String POWER_ID = "WreathOfFlamePower";
    private static final PowerStrings powerStrings;
    
    public WreathOfFlamePower(AbstractCreature owner, int amount)
    {
        this.name = "Wreath of Flame";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("vigor");
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
    }
    
    public void updateDescription()
    {
        description = "Your next Attack played while in Wrath or Divinity deals #b" + amount + " additional damage.";
    }
    
    public float atDamageGive(float damage, DamageInfo.DamageType type)
    {
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) || BcUtility.isPlayerInStance(DivinityStance.STANCE_ID))
        {
            if (type == DamageInfo.DamageType.NORMAL)
            {
                return damage + (float) this.amount;
            }
        }
        
        return damage;
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) || BcUtility.isPlayerInStance(DivinityStance.STANCE_ID))
        {
            if (card.type == AbstractCard.CardType.ATTACK)
            {
                this.flash();
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Vigor"));
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Vigor");
    }
}
