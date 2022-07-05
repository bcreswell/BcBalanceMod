package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import java.util.Iterator;

public class Chill extends BcSkillCardBase
{
    public static final String ID = "Chill";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/chill";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getChanneledOrbCount()
    {
        return getEnemyCount();
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public boolean getExhaust()
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
        return "Channel !M! Frost for each enemy in combat.";
    }
    //endregion
    
    int getEnemyCount()
    {
        int count = 0;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
        {
            if (!monster.isDeadOrEscaped())
            {
                count++;
            }
        }
        
        return count;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int count = getEnemyCount();
        
        for (int i = 0; i < count * magicNumber; i++)
        {
            addToBot(new ChannelAction(new Frost()));
        }
    }
}
