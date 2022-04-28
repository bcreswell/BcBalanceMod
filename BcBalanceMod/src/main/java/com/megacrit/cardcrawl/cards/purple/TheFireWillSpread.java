package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TheFireWillSpread extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("TheFireWillSpread");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "    The Fire Will Spread";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/theFireWillSpread.png";
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
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getMagicNumber() == 1)
        {
            return "When you Scry, NL a random enemy is damaged for the Scry amount.";
        }
        else
        {
            return "When you Scry, NL !M! random enemies are damaged for the Scry amount.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new ApplyPowerAction(player, player, new TheFireWillSpreadPower(player, magicNumber),magicNumber));
    }
}
