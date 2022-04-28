package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Nausea extends BcStatusCardBase
{
    public static final String ID = BcBalanceMod.makeID("Nausea");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Nausea";
    }
    
    @Override
    public String getImagePath()
    {
        return "status/nausea.png";
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.SELF;
    }
    
    public int getHpToLose()
    {
        return 3;
    }
    
    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "When drawn: You cannot draw additional cards this turn. NL When exhausted: NL Lose " + getHpToLose() + " HP.";
    }
    //endregion
    
    public void triggerOnExhaust()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        addToBot(new TrueWaitAction(.2f));
        
        addToBot(new VFXAction(new OfferingEffect(), 0.5F));
        addToBot(new LoseHPAction(player, player, getHpToLose()));
    }
    
    public void triggerWhenDrawn()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        addToBot(new BcApplyPowerAction(player, player, new NoDrawPower(player)));
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
}
