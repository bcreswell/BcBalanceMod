package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

public class Courier extends AbstractRelic
{
    public static final String ID = "The Courier";
    public static final float MULTIPLIER = 0.8F;
    
    public Courier()
    {
        super("The Courier", "courier.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return "The Merchant restocks cards, relics, and potions. All prices are reduced by #b20%. NL (doesn't reduce the cost of the #bCard #bRemoval #bService.)";
    }
    
    public void onEnterRoom(AbstractRoom room)
    {
        if (room instanceof ShopRoom)
        {
            this.flash();
            this.pulse = true;
        }
        else
        {
            this.pulse = false;
        }
        
    }
    
    public boolean canSpawn()
    {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }
    
    public AbstractRelic makeCopy()
    {
        return new Courier();
    }
}
