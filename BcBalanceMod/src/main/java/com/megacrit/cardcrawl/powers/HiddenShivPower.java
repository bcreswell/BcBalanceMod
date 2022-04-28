package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class HiddenShivPower extends AbstractPower
{
    public static final String POWER_ID = "Hidden Shivs";
    static int previousAmount = 0;
    static Sfx shivGainedSound;
    static int soundCd = 0;
    
    static
    {
        shivGainedSound = new Sfx(BcBalanceMod.getModID() + "Resources/sounds/STS_SFX_Shiv1_v1.ogg");
    }
    
    public HiddenShivPower(AbstractCreature owner, int shivCount)
    {
        this.name = POWER_ID;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = shivCount;
        this.updateDescription();
        this.loadRegion("painfulStabs");
    }
    
    public void update(int slot)
    {
        super.update(slot);
        
        if (soundCd > 0)
        {
            soundCd--;
            return;
        }
        
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
    
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if ((info.type == DamageInfo.DamageType.NORMAL) &&
                    ((info.name == null) || !info.name.equals(POWER_ID)) &&
                    (amount > 0) &&
                    (target instanceof AbstractMonster))
        {
            addToBot(new VFXAction(new HiddenShivEffect(owner, target), HiddenShivEffect.DelayBetweenShivs));
            addToBot(new HiddenShivDamageAction(target, owner));
        }
        
        //redundant, shouldn't actually be called
        if (amount <= 0)
        {
            addToBot(new RemoveSpecificPowerAction(owner, owner, HiddenShivPower.POWER_ID));
        }
    }
    
    public void updateDescription()
    {
        this.description = "Whenever you deal attack damage, throw a Hidden Shiv at the same target. NL Its damage is the same as a normal Shiv. NL Hidden Shivs count as attack damage, but NOT as an extra attack card being played.";
    }
}
