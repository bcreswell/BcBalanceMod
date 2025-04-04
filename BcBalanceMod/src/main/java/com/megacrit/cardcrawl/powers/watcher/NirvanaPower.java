package com.megacrit.cardcrawl.powers.watcher;

import basemod.devcommands.power.*;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.purple.Nirvana;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NirvanaPower extends BcPowerBase
{
    public static final String POWER_ID = "Nirvana";
    
    public NirvanaPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Nirvana";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "nirvana";
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
        int blockPerInsight = amount * Nirvana.BlockPerInsight;
        if (amount == 1)
        {
            return "Whenever you #yScry or gain #yMantra, also gain that amount of #yBlock.";
        }
        else
        {
            return "Whenever you #yScry or gain #yMantra, also gain that amount of #yBlock, #b" + amount + " times.";
        }
    }
    //endregion
    
    @Override
    public void onScry(int scryAmount)
    {
        flash();
        
        for (int i = 0; i < amount; i++)
        {
            addToBot(new GainBlockAction(owner, scryAmount, Settings.FAST_MODE));
        }
    }
    
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((target == AbstractDungeon.player) && (power.ID.equals(MantraPower.POWER_ID)))
        {
            flash();
            for (int i = 0; i < amount; i++)
            {
                addToBot(new GainBlockAction(owner, power.amount, Settings.FAST_MODE));
            }
        }
    }
    
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster)
    {
//        if (card.cardID.equals(Insight.ID) || card.cardID.equals(Miracle.ID))
//        {
//             int blockPerInsight = amount * Nirvana.BlockPerInsight;
//            addToBot(new GainBlockAction(owner, blockPerInsight, Settings.FAST_MODE));
//        }
    }
}
