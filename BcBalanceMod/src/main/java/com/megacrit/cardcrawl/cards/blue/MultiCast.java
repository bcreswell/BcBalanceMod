//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.MulticastAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class MultiCast extends BcSkillCardBase
{
    public static final String ID = "Multi-Cast";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/multicast";
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
        return -1;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return (getEvokeIterations() > 0) ? 1 : 0;
    }
    
    @Override
    public int getEvokeIterations()
    {
        int evokeCount = 0;
        AbstractPlayer player = AbstractDungeon.player;
        
        if (BcUtility.isPlayerInCombat() && player.hasOrb())
        {
            evokeCount = EnergyPanel.totalCount;
            if (energyOnUse > evokeCount)
            {
                evokeCount = energyOnUse;
            }
            
            if (player.hasRelic(ChemicalX.ID))
            {
                evokeCount += 2;
            }
            
            if (upgraded)
            {
                evokeCount++;
            }
            
            AbstractOrb orbToEvoke = AbstractDungeon.player.orbs.get(0);
            if ((orbToEvoke instanceof Frost) ||
                (orbToEvoke instanceof Lightning))
            {
                evokeCount++;
            }
        }
        
        return evokeCount;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "If your next Orb is Dark or Plasma, Evoke it X times. NL NL If your next Orb is Lightning or Frost, NL Evoke it X+1 times.";
        }
        else
        {
            return "If your next Orb is Dark or Plasma, Evoke it X+1 times. NL NL If your next Orb is Lightning or Frost, NL Evoke it X+2 times.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new MulticastAction(getEvokeIterations()));
    }
}