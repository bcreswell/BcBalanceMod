package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class WreathOfFlamePower extends AbstractPower
{
    public static final String POWER_ID = "WreathOfFlamePower";
    boolean hasAppliedExtraDamage;
    
    public WreathOfFlamePower(AbstractCreature owner, int amount)
    {
        name = "Wreath of Flame";
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("vigor");
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
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
                return damage + (float) amount;
            }
        }
        
        return damage;
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        //tantrum is a special case were you can start outside wrath/divinity, but you'll be in wrath/divinity by the time the damage is applied
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) ||
                    BcUtility.isPlayerInStance(DivinityStance.STANCE_ID) ||
                    (card.cardID.equals(Tantrum.ID)))
        {
            if (card.type == AbstractCard.CardType.ATTACK)
            {
                flash();
                addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
            }
        }
    }
}
