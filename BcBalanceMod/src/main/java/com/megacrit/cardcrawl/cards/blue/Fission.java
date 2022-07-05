package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.FissionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fission extends BcSkillCardBase
{
    public static final String ID = "Fission";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/fission";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 10;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Evoke all your Orbs. NL Gain [B] and draw !M! card for each Orb Evoked.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new FissionAction(true));
    }
}
