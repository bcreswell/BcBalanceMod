package com.megacrit.cardcrawl.potions;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.brashmonkey.spriter.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FelixFelicisPotion extends CustomPotion
{
    public static final String POTION_ID = BcBalanceMod.makeID("FelixFelicisPotion");
    public static final Color LiquidColor = CardHelper.getColor(251, 190, 0);
    public static final Color HybridColor = CardHelper.getColor(251, 160, 0);
    public static final int UseMultiplier = 3;
    
    int currentFloor = -1;
    
    public FelixFelicisPotion()
    {
        super("Felix Felicis Potion", POTION_ID, PotionRarity.RARE, AbstractPotion.PotionSize.EYE, PotionColor.WHITE);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = "While you possess this potion, gain #b" + potency + " #ygold whenever you climb a floor. NL NL Gain #b" + (potency * 3) + " #ygold when consumed. NL NL Warning: Can cause dangerous overconfidence if taken in excess.";
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);
        
        //putting this here cause i couldn't find a better hook. update() doesn't get regular updates.
        if (isObtained)
        {
            if (currentFloor == -1)
            {
                currentFloor = AbstractDungeon.floorNum;
            }
            
            if ((AbstractDungeon.player != null) &&
                        (currentFloor != AbstractDungeon.floorNum))
            {
                AbstractDungeon.player.gainGold(potency);
                currentFloor = AbstractDungeon.floorNum;
            }
        }
    }
    
    public boolean canUse()
    {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                    (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT))
        {
            return false;
        }
        else
        {
            return (AbstractDungeon.getCurrRoom().event == null) || !(AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain);
        }
    }
    
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.player != null)
        {
            AbstractDungeon.player.gainGold(potency * UseMultiplier);
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 7;
    }
    
    public AbstractPotion makeCopy()
    {
        return new FelixFelicisPotion();
    }
}
