package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Perseverance extends BcSkillCardBase
{
    public static final String ID = "Perseverance";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/perseverance";
    }
    
    @Override
    public int getCost()
    {
        return 1;
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
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 5 : 7;
    }
    
    @Override
    public int getMagicNumber()
    {
        //block grown on retained
        return !upgraded ? 2 : 3;
    }
    //endregion
    
    public void onRetained()
    {
        this.upgradeBlock(magicNumber);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.addToBot(new GainBlockAction(player, player, block));
    }
}
