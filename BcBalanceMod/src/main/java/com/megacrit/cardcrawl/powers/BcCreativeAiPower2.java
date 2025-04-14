//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BcCreativeAiPower2 extends BcPowerBase
{
    public static final String POWER_ID = "Creative AI2";
    boolean isUpgraded;
    
    public BcCreativeAiPower2(AbstractCreature owner, int amt)
    {
        super(owner, amt);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Creative AI";
    }
    
    @Override
    public String getId()
    {
        return !upgraded ? POWER_ID : POWER_ID + "+";
    }
    
    @Override
    public String getImagePath()
    {
        return "ai";
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
        String upgradedString = isUpgraded? "upgraded ":"";
        if (amount == 1)
        {
            return "Start of turn: NL Create a random temporary "+upgradedString+"Skill. It costs zero.";
        }
        else
        {
            return "Start of turn: NL Create #b" + amount + " random temporary "+upgradedString+"Skills. It costs zero.";
        }
    }
    //endregion
    
    public void upgrade()
    {
        isUpgraded = true;
        this.name = "Creative AI+";
        this.ID = POWER_ID + "+";
        this.updateDescription();
    }
    
    //not using atStartOfTurnPostDraw() here because i don't want it shoving ethereal powers into your discard pile when you're full.
    public void atStartOfTurn()
    {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            
            for (int i = 0; i < amount; i++)
            {
                addToBot(
                    new CreateRandomCardAction(
                        isUpgraded,
                        false,
                        false,
                        true,
                        AbstractCard.CardType.SKILL,
                        null,
                        true,
                        true,
                        true,
                        null));
            }
        }
    }
}
