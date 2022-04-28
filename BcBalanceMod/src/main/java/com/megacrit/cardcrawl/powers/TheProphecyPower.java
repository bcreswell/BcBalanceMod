//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TheProphecyPower extends AbstractPower
{
    public static final String POWER_ID = "TheProphecyPower";
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/theProphecy32x32.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/theProphecy84x84.png");
    public AbstractCard TheChosenCard;
    static int uniqueId = 0;
    
    public TheProphecyPower(AbstractCreature owner, int amount)
    {
        name = "The Prophecy";
        ID = POWER_ID + uniqueId;
        uniqueId++;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
    }
    
    public void updateDescription()
    {
        description = "When you draw #b" + ((TheChosenCard != null) ? TheChosenCard.name : "the marked card") + ", NL enter Divinity.";
    }
    
    public void onCardDraw(AbstractCard card)
    {
        if (TheChosenCard == card)
        {
            AbstractPlayer player = AbstractDungeon.player;
            if (amount > 0)
            {
                amount--;
                addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
                addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
            }
            
            addToTop(new RemoveSpecificPowerAction(player, player, ID));
        }
    }
    
    public void onExhaust(AbstractCard card)
    {
        if (TheChosenCard == card)
        {
            AbstractPlayer player = AbstractDungeon.player;
            addToTop(new RemoveSpecificPowerAction(player, player, ID));
        }
    }
}
