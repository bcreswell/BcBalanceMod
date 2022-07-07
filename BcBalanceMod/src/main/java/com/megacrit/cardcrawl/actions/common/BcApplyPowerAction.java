//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockInstance;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnAnyPowerAppliedRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;

import java.util.Collections;

public class BcApplyPowerAction extends AbstractGameAction
{
    public static final String txt0Negated;
    public static final String txt1Immune;
    public static final String txt2Nullified;
    public static final String txt3Down;
    
    static
    {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        txt0Negated = uiStrings.TEXT[0];
        txt1Immune = uiStrings.TEXT[1];
        txt2Nullified = uiStrings.TEXT[2];
        txt3Down = uiStrings.TEXT[3];
    }
    
    AbstractPower powerToApply;
    boolean isFirstUpdate = true;
    boolean isQuiet = false;
    
    //region ctors
    public BcApplyPowerAction(AbstractPower powerToApply)
    {
        this(powerToApply.owner, powerToApply.owner, powerToApply);
    }
    
    public BcApplyPowerAction(AbstractCreature target, AbstractPower powerToApply)
    {
        this(target, AbstractDungeon.player, powerToApply);
    }
    
    public BcApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply)
    {
        this(target, source, powerToApply, AttackEffect.NONE);
    }
    
    public BcApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, AttackEffect attackEffect)
    {
        if (Settings.FAST_MODE)
        {
            duration = 0F;
        }
        else
        {
            duration = Settings.ACTION_DUR_FAST;
        }
        
        setValues(target, source, powerToApply.amount);
        this.powerToApply = powerToApply;
        
        if (AbstractDungeon.player.hasRelic(SneckoSkull.ID) &&
                    (source != null) &&
                    (source.isPlayer) &&
                    (target != source) &&
                    powerToApply.ID.equals(PoisonPower.POWER_ID))
        {
            AbstractDungeon.player.getRelic(SneckoSkull.ID).flash();
            powerToApply.amount++;
            amount++;
        }
        
        if ((powerToApply instanceof BcPowerBase) &&
                    ((BcPowerBase) powerToApply).isAppliedQuietly())
        {
            isQuiet = true;
        }
        
        actionType = ActionType.POWER;
        this.attackEffect = attackEffect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            duration = 0.0F;
            isDone = true;
        }
    }
    //endregion
    
    void firstUpdate()
    {
        if (target instanceof AbstractMonster && target.isDeadOrEscaped())
        {
            isDone = true;
            return;
        }
        
        if ((powerToApply instanceof NoDrawPower) && target.hasPower(powerToApply.ID))
        {
            isDone = true;
            return;
        }
        
        //region onApplyPower
        if (source != null)
        {
            for (AbstractPower pow : source.powers)
            {
                pow.onApplyPower(powerToApply, target, source);
            }
        }
        
        if (source != null)
        {
            for (BlockInstance blockInstance : BlockModifierManager.blockInstances(source))
            {
                for (AbstractBlockModifier blockModifier : blockInstance.getBlockTypes())
                {
                    // Allows changing the stackAmount
                    amount = blockModifier.onApplyPowerStacks(powerToApply, target, source, amount);
                    // Allows negating the power
                    boolean apply = blockModifier.onApplyPower(powerToApply, target, source);
                    if (!apply)
                    {
                        addToTop(new TextAboveCreatureAction(target, txt0Negated));
                        duration -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        return;
                    }
                }
            }
            
            for (AbstractPower power : source.powers)
            {
                if (power instanceof BetterOnApplyPowerPower)
                {
                    // Allows changing the stackAmount
                    amount = ((BetterOnApplyPowerPower) power).betterOnApplyPowerStacks(powerToApply, target, source, amount);
                    // Allows negating the power
                    boolean apply = ((BetterOnApplyPowerPower) power).betterOnApplyPower(powerToApply, target, source);
                    if (!apply)
                    {
                        addToTop(new TextAboveCreatureAction(target, txt0Negated));
                        duration -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        return;
                    }
                }
            }
            
            if (source.isPlayer)
            {
                for (AbstractRelic relic : AbstractDungeon.player.relics)
                {
                    if (relic instanceof OnApplyPowerRelic)
                    {
                        // Allows changing the stackAmount
                        amount = ((OnApplyPowerRelic) relic).onApplyPowerStacks(powerToApply, target, source, amount);
                        // Allows negating the power
                        boolean apply = ((OnApplyPowerRelic) relic).onApplyPower(powerToApply, target, source);
                        if (!apply)
                        {
                            addToTop(new TextAboveCreatureAction(target, txt0Negated));
                            duration -= Gdx.graphics.getDeltaTime();
                            CardCrawlGame.sound.play("NULLIFY_SFX");
                            return;
                        }
                    }
                }
            }
        }
        
        if (target != null)
        {
            for (BlockInstance blockInstance : BlockModifierManager.blockInstances(target))
            {
                for (AbstractBlockModifier blockModifier : blockInstance.getBlockTypes())
                {
                    // Allows changing the stackAmount
                    amount = blockModifier.onReceivePowerStacks(powerToApply, target, source, amount);
                    // Allows negating the power
                    boolean apply = blockModifier.onReceivePower(powerToApply, target, source);
                    if (!apply)
                    {
                        addToTop(new TextAboveCreatureAction(target, txt0Negated));
                        duration -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        return;
                    }
                }
            }
            
            for (AbstractPower power : target.powers)
            {
                if (power instanceof OnReceivePowerPower)
                {
                    // Allows changing the stackAmount
                    amount = ((OnReceivePowerPower) power).onReceivePowerStacks(powerToApply, target, source, amount);
                    // Allows negating the power
                    boolean apply = ((OnReceivePowerPower) power).onReceivePower(powerToApply, target, source);
                    if (!apply)
                    {
                        addToTop(new TextAboveCreatureAction(target, txt0Negated));
                        duration -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        return;
                    }
                }
            }
            
            if (target.isPlayer)
            {
                for (AbstractRelic relic : AbstractDungeon.player.relics)
                {
                    if (relic instanceof OnReceivePowerRelic)
                    {
                        // Allows changing the stackAmount
                        amount = ((OnReceivePowerRelic) relic).onReceivePowerStacks(powerToApply, source, amount);
                        // Allows negating the power
                        boolean apply = ((OnReceivePowerRelic) relic).onReceivePower(powerToApply, source);
                        if (!apply)
                        {
                            addToTop(new TextAboveCreatureAction(target, txt0Negated));
                            duration -= Gdx.graphics.getDeltaTime();
                            CardCrawlGame.sound.play("NULLIFY_SFX");
                            return;
                        }
                    }
                }
            }
            
            for (AbstractRelic relic : AbstractDungeon.player.relics)
            {
                if (relic instanceof OnAnyPowerAppliedRelic)
                {
                    // Allows changing the stackAmount
                    amount = ((OnAnyPowerAppliedRelic) relic).onAnyPowerApplyStacks(powerToApply, target, source, amount);
                    // Allows negating the power
                    boolean apply = ((OnAnyPowerAppliedRelic) relic).onAnyPowerApply(powerToApply, target, source);
                    if (!apply)
                    {
                        addToTop(new TextAboveCreatureAction(target, txt0Negated));
                        duration -= Gdx.graphics.getDeltaTime();
                        CardCrawlGame.sound.play("NULLIFY_SFX");
                        return;
                    }
                }
            }
        }
        //endregion
        
        //region hard coded relic stuff
        if (AbstractDungeon.player.hasRelic(ChampionsBelt.ID) && source != null && source.isPlayer && target != source && powerToApply.ID.equals(VulnerablePower.POWER_ID))
        {
            AbstractDungeon.player.getRelic(ChampionsBelt.ID).onTrigger(target);
        }
        
        if (AbstractDungeon.player.hasRelic(Ginger.ID) && target.isPlayer && powerToApply.ID.equals(WeakPower.POWER_ID))
        {
            AbstractDungeon.player.getRelic(Ginger.ID).flash();
            addToTop(new TextAboveCreatureAction(target, txt1Immune));
            duration -= Gdx.graphics.getDeltaTime();
            return;
        }
        
        if (AbstractDungeon.player.hasRelic(Turnip.ID) && target.isPlayer && powerToApply.ID.equals(FrailPower.POWER_ID))
        {
            AbstractDungeon.player.getRelic(Turnip.ID).flash();
            addToTop(new TextAboveCreatureAction(target, txt1Immune));
            duration -= Gdx.graphics.getDeltaTime();
            return;
        }
        //endregion
        
        if (target.hasPower(ArtifactPower.POWER_ID) && (powerToApply.type == AbstractPower.PowerType.DEBUFF))
        {
            //can't block debuffs if you apply them to yourself
            if (target != source)
            {
                addToTop(new TextAboveCreatureAction(target, txt0Negated));
                duration -= Gdx.graphics.getDeltaTime();
                CardCrawlGame.sound.play("NULLIFY_SFX");
                target.getPower("Artifact").flashWithoutSound();
                target.getPower("Artifact").onSpecificTrigger();
                return;
            }
        }
        
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
        
        //find an existing power to stack it with
        AbstractPower existingPower = null;
        for (AbstractPower targetPower : target.powers)
        {
            if (targetPower.ID.equals(powerToApply.ID))
            {
                existingPower = targetPower;
                break;
            }
        }
        
        if (existingPower == null)
        {
            //region do initial power application
            if ((powerToApply.type == AbstractPower.PowerType.DEBUFF) && !isQuiet)
            {
                target.useFastShakeAnimation(0.5F);
            }
            
            target.powers.add(powerToApply);
            Collections.sort(target.powers);
            powerToApply.onInitialApplication();
    
            createBuffEffect(powerToApply, true);
    
            AbstractDungeon.onModifyPower();
            powerToApply.updateDescription();
            
            if (target.isPlayer)
            {
                int buffCount = 0;
                for (AbstractPower targetPower : target.powers)
                {
                    if (targetPower.type == AbstractPower.PowerType.BUFF)
                    {
                        buffCount++;
                    }
                }
        
                if (buffCount >= 10)
                {
                    UnlockTracker.unlockAchievement("POWERFUL");
                }
            }
            //endregion
        }
        else
        {
            existingPower.stackPower(amount);
    
            //using powerToApply here instead of existingPower because we want to show the delta
            createBuffEffect(powerToApply, false);
    
            AbstractDungeon.onModifyPower();
            existingPower.updateDescription();
        }
    }
    
    void createBuffEffect(AbstractPower power, boolean initialApplication)
    {
        if (isQuiet)
        {
            return;
        }
        
        power.flash();
        
        String msg = "";
        if (initialApplication && (amount <= 1) && !power.canGoNegative)
        {
            msg = power.name;
        }
        else
        {
            if (amount < 0)
            {
                msg = amount + " " + power.name;
            }
            else
            {
                msg = "+" + amount + " " + power.name;
            }
        }
        
        float x = target.hb.cX - target.animX;
        float y = target.hb.cY + target.hb.height / 2.0F;
        boolean useBuffEffect = power.type == AbstractPower.PowerType.BUFF;
        
        if (useBuffEffect)
        {
            AbstractDungeon.effectList.add(new PowerBuffEffect(x, y, msg));
        }
        else
        {
            AbstractDungeon.effectList.add(new PowerDebuffEffect(x, y, msg));
        }
    }
    
    public void update()
    {
        if ((target == null) || target.isDeadOrEscaped() || (amount == 0))
        {
            isDone = true;
        }
        else
        {
            if (isFirstUpdate)
            {
                firstUpdate();
                isFirstUpdate = false;
            }
            
            if (duration == 0)
            {
                isDone = true;
            }
            else
            {
                tickDuration();
            }
        }
    }
}
