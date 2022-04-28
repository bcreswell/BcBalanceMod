//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DizzyPower extends AbstractPower
{
    public static final String POWER_ID = "DizzyPower";
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/dizzy32x32.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/dizzy84x84.png");
    public static final int DizzyPerEmptyDrawPile = 8;
    public static final int NauseousTheshold = 16;
    
    public DizzyPower(AbstractCreature owner, int amount)
    {
        this.name = "Dizzy (Ascension 13)";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
    }
    
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
    }
    
    public void onCardDraw(AbstractCard card)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        amount--;
        if (amount <= 0)
        {
            amount = 0;
            addToBot(new RemoveSpecificPowerAction(player, player, POWER_ID));
        }
    }
    
    public void updateDescription()
    {
        description = "Dizzy hurts decks that spin too fast. NL NL Emptying your draw pile adds #b" + DizzyPerEmptyDrawPile + " Dizzy and drawing cards reduces it. NL NL If you would go over #b" + NauseousTheshold + " Dizzy, you'll get nauseous instead.";
    }
}