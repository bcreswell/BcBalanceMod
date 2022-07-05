package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class LegSweep extends BcSkillCardBase
{
    public static final String ID = "Leg Sweep";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/leg_sweep";
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
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 11 : 14;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Apply !M! Weak. NL Gain !B! Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(monster, player, new WeakPower(monster, magicNumber, false)));
        addToBot(new GainBlockAction(player, player, block));
    }
}
