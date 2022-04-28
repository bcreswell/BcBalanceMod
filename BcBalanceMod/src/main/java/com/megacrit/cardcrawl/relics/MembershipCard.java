package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;

public class MembershipCard extends AbstractRelic
{
    public static final String ID = "Membership Card";
    public static final float MULTIPLIER = 0.5F;
    
    public MembershipCard()
    {
        super("Membership Card", "membershipCard.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public String getUpdatedDescription()
    {
        return "#b50% discount on all products! NL (except the #bCard #bRemoval #bService. Terms and conditions may apply.)";
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
    
    public AbstractRelic makeCopy()
    {
        return new MembershipCard();
    }
}
