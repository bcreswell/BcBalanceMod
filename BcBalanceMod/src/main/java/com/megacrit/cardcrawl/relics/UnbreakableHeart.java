//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.*;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
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

public class UnbreakableHeart extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("UnbreakableHeart");
    public static final int DamageCap = 35;
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("unbreakableHeart.png"));
    
    public UnbreakableHeart()
    {
        super(ID, IMG, RelicTier.RARE, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "You can only lose up to " + DamageCap + " HP per turn.";
    }
    
    public void atBattleStart()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(player, player, new UnbreakableHeartPower(player, DamageCap), DamageCap));
    }
}
