package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.MalaiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Malaise extends BcSkillCardBase
{
    public static final String ID = "Malaise";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/malaise";
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Enemy loses X Strength. Inflict X+1 Weak.";
        }
        else
        {
            return "Enemy loses X+1 Strength. Inflict X+1 Weak.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.addToBot(new MalaiseAction(player, monster, upgraded, freeToPlayOnce, energyOnUse));
    }
}
