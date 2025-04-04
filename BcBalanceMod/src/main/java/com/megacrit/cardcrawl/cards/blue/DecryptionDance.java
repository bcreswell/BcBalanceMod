package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DecryptionDancePower;

public class DecryptionDance extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("DecryptionDance");
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/decryptionDance.png";
    }
    
    @Override
    public int getCost()
    {
        return 3;
    }
    
    @Override
    public String getDisplayName()
    {
        return "Decryption Dance";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "Attacks that cost 1 or more will Channel a random Orb and NL Lose 1 Focus.";
        
        if (upgraded)
        {
            description += " NL Your Focus can no longer be negative.";
        }
        
        return description;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        DecryptionDancePower power = new DecryptionDancePower(player, 1, upgraded);
        //power.upgraded = upgraded;
        addToBot(new BcApplyPowerAction(power));
    }
}
