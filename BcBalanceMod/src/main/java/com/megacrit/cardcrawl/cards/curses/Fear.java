package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Fear extends BcCurseCardBase
{
    public static final String ID = BcBalanceMod.makeID("Fear");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Fear";
    }
    
    @Override
    public String getImagePath()
    {
        return "curse/fear.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return null;
    }
    
    @Override
    public String getFullDescription()
    {
        return "Retain. Exhaust. NL End of turn: Lose 1 HP. NL When exhausted: NL Suffer 1 Vulnerable.";
    }
    //endregion
    
    @Override
    public void triggerOnExhaust()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new TrueWaitAction(.2f));
        addToBot(new BcApplyPowerAction(player, player, new VulnerablePower(player,1 ,false)));
    }
    
    public void triggerOnEndOfTurnForPlayingCard()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new TrueWaitAction(.1f));
        flash();
        addToBot(new LoseHPAction(player, player, magicNumber));
        addToBot(new TrueWaitAction(.1f));
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
}
