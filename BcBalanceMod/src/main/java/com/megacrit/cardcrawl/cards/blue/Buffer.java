package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class Buffer extends BcPowerCardBase
{
    public static final String ID = "Buffer";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/power/buffer";
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
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    public static int getDamagePreventionThreshold()
    {
        return 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        int dmgThreshold = getDamagePreventionThreshold();
        if (magicNumber == 1)
        {
            return "Prevent the next time an enemy's attack would deal "+dmgThreshold+" or more damage.";
        }
        else
        {
            return "Prevent the next !M! times an enemy's attack would deal "+dmgThreshold+" or more damage.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new BufferPower(player, magicNumber)));
    }
}
