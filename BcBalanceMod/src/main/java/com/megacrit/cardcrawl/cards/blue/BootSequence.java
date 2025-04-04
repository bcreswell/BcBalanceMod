package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class BootSequence extends BcSkillCardBase
{
    public static final String ID = "BootSequence";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Boot Sequence";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/boot_sequence";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
    public boolean getInnate()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }

    @Override
    public int getBlock()
    {
        return 10;
    }

    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 0 : 1;
    }

    @Override
    public String getBaseDescription()
    {
        int artifactCount = getMagicNumber();
        if (artifactCount > 0)
        {
            return "Gain !B! Block. NL Gain "+artifactCount+" Artifact.";
        }
        else
        {
            return "Gain !B! Block.";
        }
    }
    
    @Override
    public String getFootnote()
    {
        if (upgraded)
        {
            return ArtifactPower.Footnote;
        }
        
        return null;
    }
    
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));

        int artifactCount = getMagicNumber();
        if (artifactCount > 0)
        {
            addToBot(new BcApplyPowerAction(new ArtifactPower(player, artifactCount)));
        }
    }
}
