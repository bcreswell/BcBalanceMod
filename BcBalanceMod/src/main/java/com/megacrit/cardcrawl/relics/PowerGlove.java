//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.*;
import basemod.abstracts.CustomRelic;
import bcBalanceMod.*;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.tools.reflect.*;

import java.lang.reflect.*;

import static bcBalanceMod.BcBalanceMod.makeRelicOutlinePath;
import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class PowerGlove extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("PowerGlove");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("powerGlove.png"));
    private static final Texture outline = TextureLoader.getTexture(makeRelicOutlinePath("powerGlove.png"));
    boolean isEnergyGainAvailable;
    
    public PowerGlove()
    {
        super(ID, IMG, outline, RelicTier.BOSS, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "Start each combat with 1 Strength. NL NL Each Turn: The first time you play a card that costs 2 or more, gain 1 Energy.";
    }
    
    @Override
    public void atBattleStart() 
    {
        flash();
        
        addToTop(new BcApplyPowerAction(new StrengthPower(AbstractDungeon.player, 1)));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    @Override
    public void atTurnStart()
    {
        isEnergyGainAvailable = true;
        grayscale = false;
    }
    
    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction)
    {
        if (isEnergyGainAvailable &&
                    (BcUtility.getActualCardCost(targetCard) >= 2))
        {
            isEnergyGainAvailable = false;
            grayscale = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }
    
    @Override
    public void onVictory()
    {
        grayscale = false;
    }
    
    public boolean checkTrigger() {
        return isEnergyGainAvailable;
    }
}
