package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class RushdownPower extends BcPowerBase
{
    public static final String POWER_ID = "Adaptation";
    
    public RushdownPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Rushdown";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "rushdown";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (amount == 1)
        {
            return "Whenever you enter #yWrath or #yDivinity, draw #b" + amount + " card.";
        }
        else
        {
            return "Whenever you enter #yWrath or #yDivinity, draw #b" + amount + " cards.";
        }
    }
    //endregion
    
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if (!oldStance.ID.equals(newStance.ID) &&
                    (newStance.ID.equals(WrathStance.STANCE_ID) || newStance.ID.equals(DivinityStance.STANCE_ID)))
        {
            flash();
            addToBot(new DrawCardAction(owner, amount));
        }
    }
}
