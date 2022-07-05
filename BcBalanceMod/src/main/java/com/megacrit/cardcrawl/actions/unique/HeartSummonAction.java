package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeartSummonAction extends AbstractGameAction
{
    static final Logger logger = LogManager.getLogger(HeartSummonAction.class.getName());
    ArrayList<AbstractMonster> monstersToSummon;
    
    public HeartSummonAction(ArrayList<AbstractMonster> monstersToSummon)
    {
        this.monstersToSummon = monstersToSummon;
        actionType = AbstractGameAction.ActionType.SPECIAL;
        startDuration = Settings.ACTION_DUR_FAST;
        duration = startDuration;
    }
    
    public void update()
    {
        if (duration == startDuration)
        {
            for (AbstractMonster monster : monstersToSummon)
            {
                monster.animX = 1200.0F * Settings.xScale;
                monster.init();
                monster.applyPowers();
                
                AbstractDungeon.getCurrRoom().monsters.monsters.add(0, monster);
                if (ModHelper.isModEnabled("Lethality"))
                {
                    addToBot(new ApplyPowerAction(monster, monster, new StrengthPower(monster, 3), 3));
                }
                
                if (ModHelper.isModEnabled("Time Dilation"))
                {
                    addToBot(new ApplyPowerAction(monster, monster, new SlowPower(monster, 0)));
                }
                
                for (AbstractRelic relic : AbstractDungeon.player.relics)
                {
                    relic.onSpawnMonster(monster);
                }
            }
        }
        
        tickDuration();
        
        if (isDone)
        {
            for (AbstractMonster monster : monstersToSummon)
            {
                monster.animX = 0.0F;
                monster.showHealthBar();
                monster.usePreBattleAction();
            }
        }
        else
        {
            for (AbstractMonster monster : monstersToSummon)
            {
                monster.animX = Interpolation.fade.apply(0.0F, 1200.0F * Settings.xScale, duration);
            }
        }
    }
}
