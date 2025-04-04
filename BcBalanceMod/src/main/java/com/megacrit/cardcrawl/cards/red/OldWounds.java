package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;

public class OldWounds extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("OldWounds");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Old Wounds";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/oldWounds.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
        
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 4;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 6 : 8;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Draw !M! Status Cards. NL Gain !B! Block for each.";
    }
    //endregion
    
    int getPossibleDrawCount()
    {
        int statusCount = 0;
        for (AbstractCard drawPileCard : AbstractDungeon.player.drawPile.group)
        {
            if (drawPileCard.type == CardType.STATUS)
            {
                statusCount++;
            }
        }
        
        return statusCount;
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        return "possible draws: " + getPossibleDrawCount();
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return getPossibleDrawCount() > 0;
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new OldWoundsAction(magicNumber, false, getBlock()));
    }
}
