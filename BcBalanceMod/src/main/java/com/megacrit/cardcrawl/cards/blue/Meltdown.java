package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Meltdown extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("Meltdown");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Meltdown!";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/meltdown.png";
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 8 : 12;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Remove enemy's Block. NL Evoke ALL Orbs. NL Deal !D! damage for each Orb Evoked.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int orbCount = BcUtility.getFilledOrbSlotCount();
        
        addToBot(new VFXAction(player, new ScreenOnFireEffect(), 1.0F));
        //addToBot(new VFXAction(player, new FlameBarrierEffect(player.hb.cX, player.hb.cY, true), 1.5F));
        addToBot(new RemoveAllBlockAction(monster, player));
        addToBot(new EvokeAllOrbsAction());
        
        for(int i = 0; i < orbCount; i++)
        {
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
