//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.*;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;

import static bcBalanceMod.BcBalanceMod.makeRelicOutlinePath;
import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class RotaryMechanism extends CustomRelic implements ClickableRelic
{
    public static final String ID = BcBalanceMod.makeID("RotaryMechanism");
    public static final int RotationsPerCombat = 3;
    static final Texture IMG = TextureLoader.getTexture(makeRelicPath("rotaryMechanism.png"));
    static final Texture outline = TextureLoader.getTexture(makeRelicOutlinePath("rotaryMechanism.png"));
    boolean isEnabled;
    
    public RotaryMechanism()
    {
        super(ID, IMG, outline, RelicTier.RARE, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "Up to #b" + RotationsPerCombat + " times per combat: NL Right click to rotate your orbs clockwise without evoking them.";
    }
    
    public void atTurnStartPostDraw()
    {
        isEnabled = true;
    }
    
    public void onPlayerEndTurn()
    {
        isEnabled = false;
    }
    
    public void atBattleStart()
    {
        setCounter(3);
    }
    
    public void onVictory()
    {
        //just to clarify that charges don't persist between combats.
        // Also the counter gets in the way of the notch -> hurts visual clarity.
        setCounter(0);
        grayscale = false;
        usedUp = false;
    }
    
    @Override
    public void onRightClick()
    {
        if (BcUtility.isPlayerInCombat() && isEnabled && (counter > 0))
        {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new RecursionAction(false, false));
            setCounter(counter - 1);
            flash();
        }
    }
    
    @Override
    public void setCounter(int counter)
    {
        this.counter = counter;
        if (counter <= 0)
        {
            this.counter = -2;
            grayscale = true;
            usedUp = true;
        }
        else
        {
            grayscale = false;
            usedUp = false;
        }
    }
}
