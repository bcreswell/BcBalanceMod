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
    static Texture[] images = new Texture[4];
    
    public MonkeyPaw()
    {
        super(ID, IMG3, RelicTier.COMMON, LandingSound.FLAT);
        
        images[0] = IMG0;
        images[1] = IMG1;
        images[2] = IMG2;
        images[3] = IMG3;
        
        //wishesRemaining
        setCounter(3);
    }
    
    public String getUpdatedDescription()
    {
        return "Right click to Wish for another card from the previous card rewards. NL #b3 Wishes total. NL Boss Rare Cards use up #b2 Wishes. NL (Can't use during combat)";
    }
    
    @Override
    public void setCounter(int counter)
    {
        this.counter = counter;
        if (counter <= 0)
        {
            //i think this gets rid of the number
            this.counter = -2;
            this.grayscale = true;
            this.usedUp = true;
        }
        
        updateImage();
    }
    
    @Override
    public void onRightClick()
    {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if ((room != null) && (room.phase == AbstractRoom.RoomPhase.COMPLETE))
        {
            if ((counter > 0) &&
                        (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.CARD_REWARD) &&
                        (AbstractDungeon.cardRewardScreen.rewardGroup != null) &&
                        (AbstractDungeon.cardRewardScreen.rewardGroup.size() > 0))
            {
                int requiredWishes = 1;
                
                if (AbstractDungeon.cardRewardScreen.rItem != null)
                {
                    boolean isBoss = false;
                    
                    Class clz = AbstractDungeon.cardRewardScreen.rItem.getClass();
                    Field field = null;
                    try
                    {
                        field = clz.getDeclaredField("isBoss");
                        field.setAccessible(true);
                        Object obj = field.get(AbstractDungeon.cardRewardScreen.rItem);
                        if (obj instanceof Boolean)
                        {
                            isBoss = (boolean) obj;
                        }
                    }
                    catch (NoSuchFieldException | IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                    
                    if (isBoss)
                    {
                        requiredWishes = 2;
                    }
                }
                
                for (int i = 0; i < AbstractDungeon.cardRewardScreen.rewardGroup.size(); i++)
                {
                    for (AbstractCard existingCard : AbstractDungeon.player.masterDeck.group)
                    {
                        if (existingCard == AbstractDungeon.cardRewardScreen.rewardGroup.get(i))
                        {
                            AbstractDungeon.cardRewardScreen.rewardGroup.remove(i);
                            i--;
                        }
                    }
                }
                
                if ((AbstractDungeon.cardRewardScreen.rewardGroup.size() > 0) && (counter >= requiredWishes))
                {
                    flash();
                    setCounter(counter - requiredWishes);
                    
                    AbstractDungeon.cardRewardScreen.open(
                            AbstractDungeon.cardRewardScreen.rewardGroup,
                            AbstractDungeon.cardRewardScreen.rItem,
                            "Choose Another Card");
                }
            }
            
        }
    }
    
    void updateImage()
    {
        int imgIndex = BcUtility.clamp(0, counter, 3);
        setTexture(images[imgIndex]);
    }
}
