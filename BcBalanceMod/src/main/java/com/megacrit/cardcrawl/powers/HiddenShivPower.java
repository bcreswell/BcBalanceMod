package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class HiddenShivPower extends BcPowerBase
{
    public static final String POWER_ID = "Hidden Shivs";
    static int previousAmount = 0;
    static Sfx shivGainedSound;
    static int soundCd = 0;
    
    static
    {
        shivGainedSound = new Sfx(BcBalanceMod.getModID() + "Resources/sounds/STS_SFX_Shiv1_v1.ogg");
    }
    
    public HiddenShivPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "Hidden Shivs";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "painfulStabs";
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
        return "Whenever you deal attack damage, throw one of your #yHidden #yShivs at the same target. NL Its damage is the same as a normal Shiv.";
    }
    //endregion
    
    public void update(int slot)
    {
        super.update(slot);
        
        if (soundCd > 0)
        {
            soundCd--;
        }
        else
        {
            if (amount > previousAmount)
            {
                previousAmount++;
                if (!CardCrawlGame.MUTE_IF_BG || !Settings.isBackgrounded)
                {
                    flashWithoutSound();
                    shivGainedSound.play(Math.max(Settings.SOUND_VOLUME, Settings.MASTER_VOLUME), 1.3f, 0);
                    soundCd = 8;
                }
            }
            else if (amount < previousAmount)
            {
                previousAmount = amount;
            }
        }
    }
    
    public void onRemove()
    {
        previousAmount = 0;
    }
    
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if ((info.type == DamageInfo.DamageType.NORMAL) &&
                    ((info.name == null) || !info.name.equals(POWER_ID)) && //dont trigger a hidden shiv from another hidden shiv
                    (target instanceof AbstractMonster))
        {
            addToBot(new HiddenShivFlingAction(target, owner));
        }
    }
}
