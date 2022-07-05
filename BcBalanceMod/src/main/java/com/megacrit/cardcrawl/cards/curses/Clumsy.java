package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Clumsy extends BcCurseCardBase
{
    public static final String ID = "Clumsy";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "curse/clumsy";
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
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Unplayable. NL You can't draw additional cards this turn.";
    }
    //endregion
    
    public void triggerWhenDrawn()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new BcApplyPowerAction(player, player, new NoDrawPower(player)));
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    
    }
}
