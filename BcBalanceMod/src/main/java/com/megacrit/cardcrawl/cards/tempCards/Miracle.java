package com.megacrit.cardcrawl.cards.tempCards;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

public class Miracle extends BcSkillCardBase
{
    public static final String ID = "Miracle";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Miracle";
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/skill/miracle";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain "+ getEnergyString(magicNumber)+". NL Gain 1 Mantra.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!Settings.DISABLE_EFFECTS) 
        {
            addToBot(new VFXAction(new BorderFlashEffect(Color.GOLDENROD, true)));
        }

        addToBot(new VFXAction(new MiracleEffect()));
        if (upgraded)
        {
            addToBot(new GainEnergyAction(2));
        }
        else
        {
            addToBot(new GainEnergyAction(1));
        }
        
        addToBot(new BcApplyPowerAction(new MantraPower(player, 1)));
    }
}
