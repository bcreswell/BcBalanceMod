package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.Iterator;

public class WaveOfTheHandPowerDeprecated extends AbstractPower
{
    public static final String POWER_ID = "WaveOfTheHandPower";
    private static final PowerStrings powerStrings;
    
    public WaveOfTheHandPowerDeprecated(AbstractCreature owner, int newAmount)
    {
        this.name = powerStrings.NAME;
        this.ID = "WaveOfTheHandPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("wave_of_the_hand");
    }
    
    public void onGainedBlock(float blockAmount)
    {
        if (blockAmount > 0.0F)
        {
            this.flash();
            AbstractCreature player = AbstractDungeon.player;
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.NORMAL), 0.1F));
                
                addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -1), -1, true, AbstractGameAction.AttackEffect.NONE));
                
                AbstractPower artifact = monster.getPower("Artifact");
                if (artifact == null)
                {
                    addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }
    
    public void atEndOfRound()
    {
        addToBot(new RemoveSpecificPowerAction(owner, owner, "WaveOfTheHandPower"));
    }
    
    public void updateDescription()
    {
        this.description = "Whenever you gain Block this turn, NL temporarily reduce ALL Enemies' Strength by " + amount + ".";
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WaveOfTheHandPower");
    }
}
