package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Injury extends BcCurseCardBase
{
    public static final String ID = "Injury";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "curse/injury";
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
    public String getBaseDescription()
    {
        return "Unplayable. NL Draw 1 less card next turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
    
    public void triggerWhenDrawn()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new BcApplyPowerAction(player, player, new DrawReductionPower(player, 1)));
    }
}
