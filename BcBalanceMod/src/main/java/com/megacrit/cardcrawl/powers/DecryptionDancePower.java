package com.megacrit.cardcrawl.powers;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcPowerBase;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.DecryptionDance;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class DecryptionDancePower extends BcPowerBase implements OnReceivePowerPower
{
    public static final String POWER_ID = "DecryptionDancePower";
    
    boolean preventNegativeFocus = false;
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        if (!upgraded)
        {
            return "Decryption Dance";
        }
        else
        {
            return "Decryption Dance+";
        }
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "hymn";
    }
    
    @Override
    public AbstractPower.PowerType getPowerType()
    {
        return AbstractPower.PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "";
        if (amount == 1)
        {
            description += "Attacks that cost 1 or more will Channel a random Orb and Lose 1 Focus.";
        }
        else
        {
            description += "Attacks that cost 1 or more will Channel "+amount+" random Orbs and Lose "+amount+" Focus.";
        }
        
        if (preventNegativeFocus)
        {
            description += " NL Your Focus can no longer be negative.";
        }
        
        return description;
    }
    //endregion
    
    public DecryptionDancePower(AbstractCreature owner, int amt, boolean preventNegativeFocus)
    {
        super(owner, amt);
        this.preventNegativeFocus = preventNegativeFocus;
    }
    
    @Override
    public void atStartOfTurn()
    {
        updateDescription();
    }
    
    @Override
    public void stackPower(int stackAmount, AbstractPower newPower)
    {
        super.stackPower(stackAmount);
        
        if (newPower != null)
        {
            DecryptionDancePower ddPower = (DecryptionDancePower) newPower;
            if (!preventNegativeFocus &&
                ddPower.preventNegativeFocus)
            {
                preventNegativeFocus = true;
                onInitialApplication();
            }
        }
    }
    
    public void onInitialApplication()
    {
        if (preventNegativeFocus)
        {
            //remove negative focus when first applied
            int currentFocus = BcUtility.getCurrentFocus();
            if (currentFocus < 0)
            {
                addToBot(new RemovePowerIfEmptyAction(owner, FocusPower.POWER_ID));
            }
        }
    }
    
    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        if ((card.type == AbstractCard.CardType.ATTACK) &&
            (card.costForTurn > 0))
        {
            flash();
            
            for (int i = 0; i < amount; i++)
            {
                addToBot(new TrueWaitAction(.1f));
                addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));

                //addToBot(new BcApplyPowerAction(new FocusPower(owner, -1)));

                //it would be negated anyway, but got sick of seeing "Negated" after every attack.
                if (!preventNegativeFocus ||
                    (BcUtility.getCurrentFocus() > 0))
                {
                    addToBot(new BcApplyPowerAction(new FocusPower(owner, -1)));
                }
            }
        }
    }
    
    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((power instanceof FocusPower) &&
            preventNegativeFocus)
        {
            int currentFocus = BcUtility.getCurrentFocus();
            int projectedFocus = currentFocus + power.amount;
            if (projectedFocus < 0)
            {
                if (currentFocus > 0)
                {
                    //apply our own focus down power that will take it exactly down to zero.
                    addToBot(new BcApplyPowerAction(owner, owner, new FocusPower(owner, -currentFocus)));
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
