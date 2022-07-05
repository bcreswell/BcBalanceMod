package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.*;

public class ReinforcedBody extends BcSkillCardBase
{
    public static final String ID = "Reinforced Body";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/reinforced_body";
    }
    
    @Override
    public int getCost()
    {
        return -1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 7 : 9;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block X times.";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        return "!B! x " + getXCount() + " = " + getFinalBlockString() + " block";
    }
    //endregion
    
    int getXCount()
    {
        if (!BcUtility.isPlayerInCombat())
        {
            return 0;
        }
        
        AbstractPlayer player = AbstractDungeon.player;
        int x = EnergyPanel.totalCount;
        if (player.hasRelic(ChemicalX.ID))
        {
            x += 2;
        }
        
        return x;
    }
    
    String getFinalBlockString()
    {
        int x = getXCount();
        int unmoddifiedFinalBlock = baseBlock * x;
        int finalBlock = block * x;
        
        return BcUtility.getModifiedValueString(unmoddifiedFinalBlock, finalBlock);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int x = getXCount();
    
        if (!freeToPlayOnce)
        {
            player.energy.use(EnergyPanel.totalCount);
        }
        
        for (int i = 0; i < x; i++)
        {
            addToBot(new GainBlockAction(player, player, block));
        }
    }
}
