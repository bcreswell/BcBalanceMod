//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.*;
import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;

public class WaxOnPower extends AbstractPower
{
    public static final String POWER_ID = "WaxOnPower";
    
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/waveTheOtherHand36x36.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/waveTheOtherHand84x84.png");
    
    public WaxOnPower(AbstractCreature owner, int amount)
    {
        this.name = "Wax On";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
    }
    
    public void atStartOfTurnPostDraw()
    {
        AbstractPlayer player = AbstractDungeon.player;
        AbstractPower waxOffPower = player.getPower(WaxOffPower.POWER_ID);
        if (waxOffPower != null)
        {
            waxOffPower.amount = amount;
        }
        else
        {
            addToBot(new ApplyPowerAction(player, player, new WaxOffPower(player, amount)));
        }
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = "Each turn: NL The first time you Discard, NL Draw a card.";
        }
        else
        {
            description = "Each turn: NL The first " + amount + " times you Discard, NL Draw a card.";
        }
    }
}
