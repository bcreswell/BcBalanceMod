package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;

public class ImpossiblyFast extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImpossiblyFast");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Impossibly Fast";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/impossiblyFast.png";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw !M! zero cost cards. NL Add a Dazed to your draw pile.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ImpossiblyFastAction(magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
    }
}
