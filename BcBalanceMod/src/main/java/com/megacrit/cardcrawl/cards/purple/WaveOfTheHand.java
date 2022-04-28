package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.WaveOfTheHandPower;
import com.megacrit.cardcrawl.stances.*;

public class WaveOfTheHand extends BcSkillCardBase
{
    public static final String ID = "WaveOfTheHand";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/wave_of_the_hand";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    int getActualWeakCount()
    {
        int weakCount = getMagicNumber();
        if (BcUtility.isPlayerInStance(CalmStance.STANCE_ID))
        {
            weakCount++;
        }
        
        return weakCount;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Whenever you gain Block this turn, inflict !M! Weak to ALL enemies. NL Calm: Inflict " + (getMagicNumber() + 1) + " Weak instead.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int weakCount = getActualWeakCount();
        addToBot(new ApplyPowerAction(player, player, new WaveOfTheHandPower(player, weakCount), weakCount));
    }
}
