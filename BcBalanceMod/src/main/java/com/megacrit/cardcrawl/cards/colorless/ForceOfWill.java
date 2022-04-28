package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;

public class ForceOfWill extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("ForceOfWill");
    
    @Override
    public String getImagePath()
    {
        return "colorless/forceOfWill.png";
    }
    
    //region card parameters
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Force of Will";
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
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict !M! Weak on ALL enemies.";
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ALL_ENEMY;
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            addToBot(new ApplyPowerAction(monster, player, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
