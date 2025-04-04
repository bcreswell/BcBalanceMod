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
        return "Right click to Wish for new card choices. NL #b3 Wishes total. NL Boss Card Rewards use up #b2 Wishes. NL Can also spend a Wish to bring the card rewards back up and pick another card.";
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
        if ((counter > 0) &&
            (room != null) &&
            (room.phase == AbstractRoom.RoomPhase.COMPLETE) &&
            (AbstractDungeon.cardRewardScreen.rewardGroup != null) &&
            (AbstractDungeon.cardRewardScreen.rewardGroup.size() > 0))
        {
            //region calculate required wishes
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
            //endregion
            
            if (counter >= requiredWishes)
            {
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD)
                {
                    //region Wish to reroll current card options
                    flash();
                    setCounter(counter - requiredWishes);
                    
                    AbstractDungeon.cardRewardScreen.rewardGroup.clear();
                    AbstractDungeon.cardRewardScreen.rewardGroup.addAll(AbstractDungeon.getRewardCards());
                    
                    AbstractDungeon.cardRewardScreen.open(
                            AbstractDungeon.cardRewardScreen.rewardGroup,
                            AbstractDungeon.cardRewardScreen.rItem,
                            "You wish to see different cards?");
                    //endregion
                }
                else
                {
                    //region Wish to see card rewards again
                    for (int i = 0; i < AbstractDungeon.cardRewardScreen.rewardGroup.size(); i++)
                    {
                        for (AbstractCard existingCard : AbstractDungeon.player.masterDeck.group)
                        {
                            if (existingCard == AbstractDungeon.cardRewardScreen.rewardGroup.get(i))
                            {
                                AbstractDungeon.cardRewardScreen.rewardGroup.remove(i);
                                i--;
                                break;
                            }
                        }
                    }
                    
                    if (AbstractDungeon.cardRewardScreen.rewardGroup.size() > 0)
                    {
                        flash();
                        setCounter(counter - requiredWishes);
                        
                        AbstractDungeon.cardRewardScreen.open(
                                AbstractDungeon.cardRewardScreen.rewardGroup,
                                AbstractDungeon.cardRewardScreen.rItem,
                                "Choose Another Card");
                    }
                    //endregion
                }
            }
        }
    }
    
//    private void placeCards()
//    {
//        float x = Settings.WIDTH / 2.0F;
//        float y = (float)Settings.HEIGHT * 0.45F;;
//        int maxPossibleStartingIndex = AbstractDungeon.cardRewardScreen.rewardGroup.size() - 4;
//        int indexToStartAt = (int) ((float) (maxPossibleStartingIndex + 1) * MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.scrollX));
//        if (indexToStartAt > maxPossibleStartingIndex)
//        {
//            indexToStartAt = maxPossibleStartingIndex;
//        }
//
//        AbstractCard c;
//        for (Iterator var5 = this.rewardGroup.iterator(); var5.hasNext(); c.current_y = y)
//        {
//            c = (AbstractCard) var5.next();
//            c.drawScale = 0.75F;
//            c.targetDrawScale = 0.75F;
//            if (this.rewardGroup.size() > 5)
//            {
//                if (this.rewardGroup.indexOf(c) < indexToStartAt)
//                {
//                    c.current_x = (float) (-Settings.WIDTH) * 0.25F;
//                }
//                else if (this.rewardGroup.indexOf(c) >= indexToStartAt + 4)
//                {
//                    c.current_x = (float) Settings.WIDTH * 1.25F;
//                }
//                else
//                {
//                    c.current_x = x;
//                }
//            }
//            else
//            {
//                c.current_x = x;
//            }
//        }
//    }
    
    void updateImage()
    {
        int index = BcUtility.clamp(0, counter, 3);
        setTextureOutline(images[index], outlines[index]);
    }
}
