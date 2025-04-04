package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Panacea extends BcSkillCardBase
{
    public static final String ID = "Panacea";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/panacea";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }

    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Cure ALL Vulnerable. NL Gain !M! Artifact.";
    }
    
    @Override
    public String getFootnote()
    {
        return ArtifactPower.Footnote;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (BcUtility.playerHasPower(VulnerablePower.POWER_ID))
        {
            addToBot(new RemoveSpecificPowerAction(player, player, VulnerablePower.POWER_ID));
        }
        
        addToBot(new BcApplyPowerAction(new ArtifactPower(player, magicNumber)));
    }
}
