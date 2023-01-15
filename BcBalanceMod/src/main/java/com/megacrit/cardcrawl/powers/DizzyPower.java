//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DizzyPower extends BcPowerBase
{
    public static final String POWER_ID = "DizzyPower";
    
    public static final int DizzyPerShuffle = 10;
    public static final int NauseousTheshold = 30;
    
    public DizzyPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Dizzy (Ascension 13)";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "dizzy32x32.png";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.DEBUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public boolean isAppliedQuietly()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Shuffling causes you to suffer #b" + DizzyPerShuffle +
                       " Dizzy. Each card you draw reduces your Dizzy by 1. NL NL If you would go over #b" +
                       NauseousTheshold + " Dizzy, you'll get nauseous instead.";
    }
    //endregion
    
    public void onCardDraw(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        amount--;
        if (amount <= 0)
        {
            amount = 0;
            //we say "if empty" here because more dizzy might be added before it resolves. dont want to remove the power in that case.
            addToTop(new RemovePowerIfEmptyAction(player, POWER_ID, true));
        }
    }
}