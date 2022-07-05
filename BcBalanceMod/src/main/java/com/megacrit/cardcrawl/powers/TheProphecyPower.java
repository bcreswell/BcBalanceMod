//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
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
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TheProphecyPower extends BcPowerBase
{
    public static final String POWER_ID = "TheProphecyPower";
    public AbstractCard TheChosenCard;
    
    public TheProphecyPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
        name = "The Prophecy";
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "The Prophecy";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "theProphecy32x32.png";
    }
    
    @Override
    public BuffDebuffApplicationType getApplicationType()
    {
        return BuffDebuffApplicationType.SeparateApplications;
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
        String cardName = "the marked card";
        if (TheChosenCard != null)
        {
            cardName = "[ " + TheChosenCard.name + " ]";
        }
        
        return "When you draw " + cardName + ", NL enter Divinity.";
    }
    //endregion
    
    public void onCardDraw(AbstractCard card)
    {
        if (TheChosenCard == card)
        {
            AbstractPlayer player = AbstractDungeon.player;
    
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            addToBot(new SFXAction("HEAL_1"));
            addToBot(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
            addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
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
