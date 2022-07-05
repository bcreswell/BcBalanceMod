package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.*;

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
        return "Enter Wrath. NL Deal !D! damage !M! times. NL Shuffle this back into your draw pile.";
//        if (!upgraded)
//        {
//            return "Enter Wrath. NL Deal !D! damage !M! times.";
//        }
//        else
//        {
//            return "Enter Wrath. NL Deal !D! damage !M! times. NL Shuffle this back into your draw pile.";
//        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        addToBot(new TantrumAction(this, player, monster, getDamage(), getMagicNumber()));
    
        shuffleBackIntoDrawPile = true;
//        if (upgraded)
//        {
//            shuffleBackIntoDrawPile = true;
//        }
    }
}
