//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;
import com.megacrit.cardcrawl.actions.animations.VFXAction;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeNextOrbOfTypeAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.badlogic.gdx.graphics.*;

import java.util.Collections;
import java.util.Objects;

public class CoreSurge extends BcAttackCardBase
{
    public static final String ID = "Core Surge";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/core_surge";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 11;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 10;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        int count = 0;
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            String targetOrbType = AbstractDungeon.player.orbs.get(0).ID;
            
            for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
            {
                if (Objects.equals(AbstractDungeon.player.orbs.get(i).ID, targetOrbType))
                {
                    count++;
                }
            }
        }
        
        return Math.min(count, getMagicNumber());
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Evoke your next Orb and all Orbs of that type.";
    }
    //endregion
    
    @Override
    public void showWhichOrbsWillEvoke(int evokedOrbsCount, int evokeTimes)
    {
        if (!AbstractDungeon.player.orbs.isEmpty())
        {
            String targetOrbType = AbstractDungeon.player.orbs.get(0).ID;
            
            int count = 0;
            for (AbstractOrb orb : AbstractDungeon.player.orbs)
            {
                if ((count < evokedOrbsCount) &&
                            (Objects.equals(orb.ID, targetOrbType)))
                {
                    orb.showEvokeValue();
                    orb.evokeTimes = evokeTimes;
                    count++;
                }
            }
        }
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.BLUNT_HEAVY));
        
        int toEvokeCount = getNumberOfOrbsEvokedDirectly();
        if (toEvokeCount > 0)
        {
            String targetOrbType = AbstractDungeon.player.orbs.get(0).ID;
            
            if (targetOrbType == Frost.ORB_ID)
            {
                addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
            }
            else if (targetOrbType == Lightning.ORB_ID)
            {
                addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.LIGHT_YELLOW_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
            }
            else if (targetOrbType == Dark.ORB_ID)
            {
                addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.PURPLE_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
            }
            
            addToBot(new TrueWaitAction(.1f));
            
            for (int i = 0; i < toEvokeCount; i++)
            {
                addToBot(new EvokeNextOrbOfTypeAction(targetOrbType));
                addToBot(new TrueWaitAction(.05f));
            }
        }
    }
}