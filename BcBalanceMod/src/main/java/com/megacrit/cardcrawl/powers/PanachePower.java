package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.vfx.combat.*;

public class PanachePower extends AbstractPower
{
    public static final String POWER_ID = "Panache";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int CARD_AMT = 5;
    private int damage;
    
    public PanachePower(AbstractCreature owner, int damage)
    {
        name = NAME;
        ID = "Panache";
        this.owner = owner;
        amount = 5;
        this.damage = damage;
        updateDescription();
        loadRegion("panache");
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + damage + DESCRIPTIONS[2];
        }
        else
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[3] + damage + DESCRIPTIONS[2];
        }
    }
    
    public void stackPower(int stackAmount)
    {
        fontScale = 8.0F;
        damage += stackAmount;
        updateDescription();
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        --amount;
        if (amount == 0)
        {
            flash();
            amount = 5;
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.LIGHT_YELLOW_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.3F));
            
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        
        updateDescription();
    }
    
    public void atStartOfTurn()
    {
        amount = 5;
        updateDescription();
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Panache");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
