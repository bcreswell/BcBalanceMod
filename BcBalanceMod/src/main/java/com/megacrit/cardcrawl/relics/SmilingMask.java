package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

public class SmilingMask extends AbstractRelic
{
    public static final String ID = "Smiling Mask";
    public static final int COST = 50;
    public static final int CostReduction = 25;
    
    public SmilingMask()
    {
        super("Smiling Mask", "merchantMask.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        
        return "The Merchant's #bCard #bRemoval #bService now costs #y" + CostReduction + "g less for each removal.";
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
        return new SmilingMask();
    }
}
