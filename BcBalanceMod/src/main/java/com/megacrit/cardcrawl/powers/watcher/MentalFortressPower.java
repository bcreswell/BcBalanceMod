//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;

public class MentalFortressPower extends BcPowerBase
{
    public static final String POWER_ID = "Controlled";
    
    public MentalFortressPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Mental Fortress";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "mental_fortress";
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
        return "Whenever you change Stances, gain #b" + amount + " #yBlock.";
    }
    //endregion
    
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance)
    {
        if (!oldStance.ID.equals(newStance.ID))
        {
            flash();
            addToBot(new GainBlockAction(owner, owner, amount));
        }
    }
}
