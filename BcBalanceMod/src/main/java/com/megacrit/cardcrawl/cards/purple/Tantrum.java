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
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        if (!BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            //doesn't do damage when not in Wrath.
            return CardTarget.NONE;
        }
        else
        {
            return super.getCardTarget();
        }
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isPlayerInStance(WrathStance.STANCE_ID),
            "#gWrath: Deal !D! damage !M! times to random enemies.",
            "#gElse: Enter Wrath and Shuffle this into your draw pile.");
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            for(int i = 0; i < magicNumber; i++)
            {
                addToBot(new AttackDamageRandomEnemyAction(this,AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
        else
        {
            addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
            shuffleBackIntoDrawPile = true;
        }
    }
}
