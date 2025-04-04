package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;

public class HauntedWhispers extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("HauntedWhispers");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Haunted Whispers";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/hauntedWhispers.png";
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
        return CardRarity.UNCOMMON;
    }

    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Draw an Ethereal card of your choice.";
        }
        else
        {
            return "Draw or Exhume an Ethereal card of your choice.";
        }
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        int etherealCount = 0;
        
        if ((player != null) && (player.drawPile != null))
        {
            for (AbstractCard card : player.drawPile.group)
            {
                if (card.isEthereal)
                {
                    etherealCount++;
                }
            }
        }
        return "" + etherealCount + " in draw pile";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawOfPlayersChoiceAction(magicNumber, upgraded, true, false, false));
    }
}
