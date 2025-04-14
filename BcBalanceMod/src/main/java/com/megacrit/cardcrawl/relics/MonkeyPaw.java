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

public class MonkeyPaw extends CustomRelic implements ClickableRelic
{
    public static final String ID = BcBalanceMod.makeID("MonkeyPaw");
    
    private static final Texture IMG0 = TextureLoader.getTexture(makeRelicPath("monkeyPaw0.png"));
    private static final Texture IMG1 = TextureLoader.getTexture(makeRelicPath("monkeyPaw1.png"));
    private static final Texture IMG2 = TextureLoader.getTexture(makeRelicPath("monkeyPaw2.png"));
    private static final Texture IMG3 = TextureLoader.getTexture(makeRelicPath("monkeyPaw3.png"));
    private static final Texture outline0 = TextureLoader.getTexture(makeRelicOutlinePath("monkeyPaw0.png"));
    private static final Texture outline1 = TextureLoader.getTexture(makeRelicOutlinePath("monkeyPaw1.png"));
    private static final Texture outline2 = TextureLoader.getTexture(makeRelicOutlinePath("monkeyPaw2.png"));
    private static final Texture outline3 = TextureLoader.getTexture(makeRelicOutlinePath("monkeyPaw3.png"));
    static Texture[] images = new Texture[4];
    static Texture[] outlines = new Texture[4];
    
    public MonkeyPaw()
    {
        super(ID, IMG3, outline3, RelicTier.COMMON, LandingSound.FLAT);
        
        images[0] = IMG0;
        images[1] = IMG1;
        images[2] = IMG2;
        images[3] = IMG3;
        
        outlines[0] = outline0;
        outlines[1] = outline1;
        outlines[2] = outline2;
        outlines[3] = outline3;
        
        //wishes remaining
        setCounter(3);
    }
    
    public String getUpdatedDescription()
    {
        return "While viewing your card reward options, Right click to Wish for new cards rewards. NL #b3 Wishes total.";
        //return "Right click to Wish for new card reward choices. NL #b3 Wishes total.";
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
        
        updateImage();
    }
    
    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }
    
    @Override
    public void onRightClick()
    {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if ((room != null) &&
            (room.phase == AbstractRoom.RoomPhase.COMPLETE) &&
            (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) &&
            (counter > 0) &&
            (AbstractDungeon.cardRewardScreen.rewardGroup != null) &&
            (AbstractDungeon.cardRewardScreen.rewardGroup.size() > 0))
        {
            flash();
            setCounter(counter - 1);
            
            AbstractDungeon.cardRewardScreen.rewardGroup.clear();
            AbstractDungeon.cardRewardScreen.rewardGroup.addAll(AbstractDungeon.getRewardCards());
            
            AbstractDungeon.cardRewardScreen.open(
                AbstractDungeon.cardRewardScreen.rewardGroup,
                AbstractDungeon.cardRewardScreen.rItem,
                "You wish to see different cards?");
        }
    }
    
    void updateImage()
    {
        int index = BcUtility.clamp(0, counter, 3);
        setTextureOutline(images[index], outlines[index]);
    }
}
