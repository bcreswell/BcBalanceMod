//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.BcUtility;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.DecryptionDance;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class DecryptionDancePower extends AbstractPower implements OnReceivePowerPower
{
    public static final String POWER_ID = "DecryptionDancePower";
    
    public DecryptionDancePower(AbstractCreature owner, int amount)
    {
        ID = POWER_ID;
        name = "Decryption Dance";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("hymn");
    }
    
    public void updateDescription()
    {
        description = "When you play an attack card, channel a random orb and lose 1 Focus. Your Focus can no longer be negative.";
    }
    
    public void onInitialApplication()
    {
        //remove negative focus when first applied
        int currentFocus = BcUtility.getCurrentFocus();
        if (currentFocus < 0)
        {
            addToBot(new RemovePowerIfEmptyAction(owner, FocusPower.POWER_ID));
        }
    }
    
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            flash();
            
            for (int i = 0; i < amount; i++)
            {
                addToBot(new TrueWaitAction(.1f));
                addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
                
                //it would be negated anyway, but got sick of seeing "Negated" after every attack.
                if (BcUtility.getCurrentFocus() > 0)
                {
                    addToBot(new BcApplyPowerAction(new FocusPower(owner, -1)));
                }
            }
        }
    }
    
    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if (power instanceof FocusPower)
        {
            int currentFocus = BcUtility.getCurrentFocus();
            int projectedFocus = currentFocus + power.amount;
            if (projectedFocus < 0)
            {
                if (currentFocus > 0)
                {
                    //apply our own focus down power that will take it exactly down to zero.
                    addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, -currentFocus), -currentFocus));
                }
                else if (currentFocus < 0)
                {
                    addToBot(new RemoveSpecificPowerAction(owner, owner, FocusPower.POWER_ID));
                }
                
                //block the bigger focus application
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source,
                                    int stackAmount)
    {
        return stackAmount;
    }
}
