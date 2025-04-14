package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class NinjaVanish extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("NinjaVanish");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Ninja Vanish!";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "green/ninjaVanish.png";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        //draw
        return 2;
    }
    
    public int getEnergyGain()
    {
        return !upgraded ? 0 : 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Gain 1 Intangible. NL End your turn. NL Next turn, Draw "+getCardCountString(magicNumber);
        
        int energyGain = getEnergyGain();
        if (energyGain > 0)
        {
            description += " and Gain "+getEnergyString(energyGain);
        }
        
        description += ".";
        
        return description;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int energyGain = getEnergyGain();
        
        addToBot(new VFXAction(new NinjaVanishEffect(player.hb.cX, player.hb.cY)));
        
        addToBot(new BcApplyPowerAction(new IntangiblePlayerPower(player, 1)));
        
        if (magicNumber > 0)
        {
            addToBot(new BcApplyPowerAction(new DrawCardNextTurnPower(player, magicNumber)));
        }
        
        if (energyGain > 0)
        {
            addToBot(new BcApplyPowerAction(new EnergizedPower(player, energyGain)));
        }
        
        addToBot(new PressEndTurnButtonAction());
    }
}