package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class JuggernautPower extends BcPowerBase
{
    public static final String POWER_ID = "Juggernaut";
    public static final int MaxDmg = 999;
    public boolean SkipNextBlockGain = false;
    
    public JuggernautPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Juggernaut";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "juggernaut";
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
            return "When you gain #yBlock, deal that much damage to a random enemy.";
        }
        else
        {
            return "When you gain #yBlock, deal that much damage to random enemies " + amount + " times.";
        }
    }
    //endregion
    
    @Override
    public void onGainedBlock(float blockAmount)
    {
        if (blockAmount > 0.0F)
        {
            if (SkipNextBlockGain)
            {
                SkipNextBlockGain = false;
            }
            else
            {
                flash();
                
                int dmgAmount = (int) Math.min(MaxDmg, blockAmount);
                
                for (int i = 0; i < amount; i++)
                {
                    addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, dmgAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
            }
        }
    }
}
