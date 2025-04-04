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
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import java.util.Objects;

import static bcBalanceMod.BcBalanceMod.makeRelicOutlinePath;
import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class GoldenFeather extends CustomRelic implements ClickableRelic
{
    public static final String ID = BcBalanceMod.makeID("GoldenFeather");
    static final Texture IMG = TextureLoader.getTexture(makeRelicPath("goldenFeather.png"));
    static final Texture outline = TextureLoader.getTexture(makeRelicOutlinePath("goldenFeather.png"));
    public static final int StanceExitPerCombat = 2;
    boolean isEnabled;
    
    public GoldenFeather()
    {
        super(ID, IMG, outline, RelicTier.RARE, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        if (StanceExitPerCombat == 1)
        {
            return "Up to #b" + StanceExitPerCombat + " time per combat: NL Right click this relic to exit your stance.";
        }
        else
        {
            return "Up to #b" + StanceExitPerCombat + " times per combat: NL Right click this relic to exit your stance.";
        }
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
        setCounter(StanceExitPerCombat);
    }
    
    public void onVictory()
    {
        //just to clarify that charges don't persist between combats.
        setCounter(0);
        grayscale = false;
        usedUp = false;
    }
    
    @Override
    public void onRightClick()
    {
        if (BcUtility.isPlayerInCombat() && isEnabled && (counter > 0))
        {
            AbstractPlayer player = AbstractDungeon.player;
            addToBot(new NotStanceCheckAction(NeutralStance.STANCE_ID, new VFXAction(new EmptyStanceEffect(player.hb.cX, player.hb.cY), 0.1F)));
            if (AbstractDungeon.player.stance.ID != NeutralStance.STANCE_ID)
            {
                addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
                flash();
                setCounter(counter - 1);
            }
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
