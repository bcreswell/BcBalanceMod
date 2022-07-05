//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;

public class StaticDischargePower extends AbstractPower
{
    public static final String POWER_ID = "StaticDischarge";
    private static final PowerStrings powerStrings;
    int remainingChannelsForThisAttack = 0;
    
    public StaticDischargePower(AbstractCreature owner, int lightningAmount)
    {
        name = powerStrings.NAME;
        ID = "StaticDischarge";
        this.owner = owner;
        amount = lightningAmount;
        updateDescription();
        loadRegion("static_discharge");
    }
    
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if ((damageAmount > 0) &&
                    (target != owner) &&
                    (info.type == DamageType.NORMAL))
        {
            flash();
            
            for (int i = 0; i < amount; ++i)
            {
                if (remainingChannelsForThisAttack > 0)
                {
                    remainingChannelsForThisAttack--;
                    addToBot(new ChannelAction(new Lightning()));
                }
            }
        }
    }
    
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if ((info.type == DamageType.NORMAL) &&
                    (info.owner != null) &&
                    (info.owner != owner) &&
                    (damageAmount > 0))
        {
            for (int i = 0; i < amount; ++i)
            {
                addToTop(new ChannelAction(new Lightning()));
            }
        }
        
        return damageAmount;
    }
    
    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if (card.type == AbstractCard.CardType.ATTACK)
        {
            flash();
            remainingChannelsForThisAttack = amount;
        }
    }
    
    public void updateDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            description = "Whenever you take unblocked attack damage or play an Attack that deals unblocked damage, Channel #b" + amount + " Lightning.";
        }
        else
        {
            description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("StaticDischarge");
    }
}
