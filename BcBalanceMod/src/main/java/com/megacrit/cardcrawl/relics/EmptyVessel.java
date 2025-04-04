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
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;

import static bcBalanceMod.BcBalanceMod.makeRelicOutlinePath;
import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class EmptyVessel extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("EmptyVessel");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("emptyVessel.png"));
    private static final Texture outline = TextureLoader.getTexture(makeRelicOutlinePath("emptyVessel.png"));
    
    public EmptyVessel()
    {
        super(ID, IMG, outline, RelicTier.BOSS, LandingSound.SOLID);
    }
    
    public String getUpdatedDescription()
    {
        return "Gain [E] when you exit your stance by playing any of these cards: NL -Empty Fist NL -Empty Body NL -Empty Mind NL -Empty Spirit";
    }
}