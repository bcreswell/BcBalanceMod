package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.AnimateSpecificOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.RemoveSpecificOrbAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.DeepDarknessPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

import java.util.Collections;
import java.util.Iterator;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class Meltdown extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Meltdown");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Meltdown!";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/meltdown.png";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! Focus. NL Evoke ALL Orbs. NL Remove all Focus. NL Suffer 1 Vulnerable.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new RainbowCardEffect()));
        addToBot(new BcApplyPowerAction(new FocusPower(player, magicNumber)));
        addToBot(new TrueWaitAction(.5f));
        addToBot(new EvokeAllOrbsAction());
        addToBot(new RemoveSpecificPowerAction(player, player, FocusPower.POWER_ID));
//        addToBot(new SFXAction("ATTACK_HEAVY"));
//        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new BcApplyPowerAction(new VulnerablePower(player, 1, false)));
    }
}