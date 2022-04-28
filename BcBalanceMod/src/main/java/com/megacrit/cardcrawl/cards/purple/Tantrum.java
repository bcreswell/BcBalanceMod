package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Tantrum extends BcAttackCardBase
{
    public static final String ID = "Tantrum";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/tantrum";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public int getDamage()
    {
        return 3;
    }
    
    @Override
    public int getMagicNumber()
    {
        //number of attacks
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage !M! times. NL Enter Wrath. NL Shuffle this back into your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for (int i = 0; i < this.magicNumber; ++i)
        {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    
        shuffleBackIntoDrawPile = true;
        addToBot(new ChangeStanceAction("Wrath"));
//        if (!BcUtility.getStanceId().equals("Wrath"))
//        {
//            addToBot(new GainEnergyAction(1));
//        }
    }
}
