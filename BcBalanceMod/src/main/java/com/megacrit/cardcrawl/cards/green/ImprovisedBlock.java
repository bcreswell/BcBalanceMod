package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class ImprovisedBlock extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImprovisedBlock");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Improvised Block";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/improvisedBlock.png";
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
    public AbstractCard.CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return 0;
    }
    
    @Override
    public int getBlock()
    {
        return JustImprovising.getImprovisedBlock(upgraded);
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        onChoseThisOption();
    }
    
    public void onChoseThisOption()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null)
        {
            applyPowersToBlock();
            this.addToBot(new GainBlockAction(player, player, this.block));
        }
        JustImprovising.TempTarget = null;
    }
}
