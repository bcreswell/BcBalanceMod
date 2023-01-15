package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Terror extends BcSkillCardBase
{
    public static final String ID = "Terror";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/terror";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getInnate()
    {
        return false;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (upgraded)
        {
            return "Strip all Artifact from the target. NL Inflict 99 Vulnerable.";
        }
        else
        {
            return "Inflict 99 Vulnerable.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (upgraded && monster.hasPower(ArtifactPower.POWER_ID))
        {
            addToBot(new RemoveSpecificPowerAction(monster, player,  ArtifactPower.POWER_ID));
            addToBot(new TrueWaitAction(.2f));
        }
        addToBot(new BcApplyPowerAction(monster, player, new VulnerablePower(monster, 99, false)));
    }
}
