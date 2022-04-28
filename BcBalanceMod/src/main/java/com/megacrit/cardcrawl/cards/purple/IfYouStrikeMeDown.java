package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

public class IfYouStrikeMeDown extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("IfYouStrikeMeDown");
    
    //region Description
    
    @Override
    public String getDisplayName()
    {
        return "       If You Strike Me Down";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/ifYouStrikeMeDown.png";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        String d = "If you die: NL Return with " + getHpToReviveWith() + " health, NL Gain 1 Intangible, NL and on the next turn, Enter Divinity.";
        if (BcUtility.playerHasRelic(StrikeDummy.ID))
        {
            d += " NL (Strike Dummy: bonus HP)";
        }
        return d;
    }
    //endregion
    
    public static int getHpToReviveWith()
    {
        int reviveHp = 5;
        if (BcUtility.playerHasRelic(StrikeDummy.ID))
        {
            reviveHp += 5;
        }
        
        return reviveHp;
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToTop(new ApplyPowerAction(player, player, new IfYouStrikeMeDownPower(player, 1), 1));
    }
}
