package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

public class ThickSkull extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("ThickSkull");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Thick Skull";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/thickSkull.png";
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
    public int getBlock()
    {
        return !upgraded ? 5 : 8;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Shuffle a *Dazed into your draw pile.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, block));
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
    }
}
