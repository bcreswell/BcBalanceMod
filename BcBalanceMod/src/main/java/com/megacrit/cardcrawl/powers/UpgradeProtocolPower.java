//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class UpgradeProtocolPower extends AbstractPower
{
    public UpgradeProtocolPower(AbstractCreature owner, int amount)
    {
        ID = "UpgradeProtocolPower";
        name = "Upgrade Protocol";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        loadRegion("draw");
    }
    
    public void updateDescription()
    {
        description = "Start of turn: upgrade a random card in your hand.";
    }
    
    public void atStartOfTurnPostDraw()
    {
        flash();
        
        for (int i = 0; i < amount; i++)
        {
            addToBot(new WaitAction(1F));
            addToBot(new UpgradeRandomCardAction());
        }
    }
}
