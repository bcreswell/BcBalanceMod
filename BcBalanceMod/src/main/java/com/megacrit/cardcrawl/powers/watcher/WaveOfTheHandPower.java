package com.megacrit.cardcrawl.powers.watcher;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.stances.*;

import java.util.Iterator;

public class WaveOfTheHandPower extends AbstractPower
{
    public static final String POWER_ID = "WaveOfTheHandPower";
    private static final PowerStrings powerStrings;
    
    public WaveOfTheHandPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "WaveOfTheHandPower";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("wave_of_the_hand");
    }
    
    public void onGainedBlock(float blockAmount)
    {
        if (blockAmount > 0.0F)
        {
            flash();
            
            int weakCount = amount;
            if (BcUtility.isPlayerInStance(CalmStance.STANCE_ID))
            {
                weakCount++;
            }
            
            for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, weakCount, false), weakCount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
    
    public void atEndOfRound()
    {
        addToBot(new RemoveSpecificPowerAction(owner, owner, "WaveOfTheHandPower"));
    }
    
    public void updateDescription()
    {
        description =  "Whenever you gain Block this turn, inflict #b"+amount+" Weak to ALL enemies. NL Calm: Inflict #b" + (amount + 1) + " Weak instead.";
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WaveOfTheHandPower");
    }
}
