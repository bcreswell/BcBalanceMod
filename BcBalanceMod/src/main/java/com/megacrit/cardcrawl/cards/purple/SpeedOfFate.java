package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.brashmonkey.spriter.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class SpeedOfFate extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("SpeedOfFate");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Speed of Fate";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/speedOfFate.png";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        int scryAmount = BcUtility.getActualScryAmount(getMagicNumber());
        return "Scry " + scryAmount + ".";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster var2)
    {
        int scryAmount = BcUtility.getActualScryAmount(magicNumber);
        
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new ScryAction(scryAmount));
    }
}
