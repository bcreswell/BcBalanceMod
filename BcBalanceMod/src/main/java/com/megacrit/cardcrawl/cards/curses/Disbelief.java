package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;

public class Disbelief extends BcCurseCardBase
{
    public static final String ID = BcBalanceMod.makeID("Disbelief");

    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Disbelief";
    }
    
    @Override
    public String getImagePath()
    {
        return "curse/disbelief.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When you play a card, also discard 1 at random.";
    }
    //endregion
    
    public void triggerOnOtherCardPlayed(AbstractCard card)
    {
        flash();
        addToBot(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
}
