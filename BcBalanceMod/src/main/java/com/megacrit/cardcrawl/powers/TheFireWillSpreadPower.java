//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheFireWillSpreadPower extends AbstractPower
{
    public static final String POWER_ID = "TheFireWillSpreadPower";
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/theFireWillSpread35x35.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/theFireWillSpread84x84.png");
    
    public TheFireWillSpreadPower(AbstractCreature owner, int amount)
    {
        name = "The Fire Will Spread";
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
    }
    
    public void updateDescription()
    {
        if (amount == 1)
        {
            description = "When you Scry, NL a random enemy is damaged for the Scry amount.";
        }
        else
        {
            description = "When you Scry, NL " + amount + " random enemies are damaged for the Scry amount.";
        }
    }
}