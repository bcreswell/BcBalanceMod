package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class SpiritShield extends BcSkillCardBase
{
    public static final String ID = "SpiritShield";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/spirit_shield";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !M! Block for every other card in your hand.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        applyPowers();
        addToBot(new GainBlockAction(player, player, block));
    }
    
    int getBaseBlock()
    {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c != this)
            {
                count++;
            }
        }
        
        return count * magicNumber;
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c != this)
            {
                count++;
            }
        }
        
        int baseBlock = getBaseBlock();
        int modifiedBlock = BcUtility.getModifiedBlock(baseBlock);
        String blockString = BcUtility.getModifiedValueString(baseBlock, modifiedBlock);
        
        return blockString + " block";
    }
    
    public void applyPowers()
    {
        baseBlock = getBaseBlock();
        super.applyPowers();
    }
}
