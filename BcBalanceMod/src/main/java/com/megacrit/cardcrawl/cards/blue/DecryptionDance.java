//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    public boolean getEthereal()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "After you play an Attack, channel a random orb and lose 1 Focus. NL Your Focus can no longer be negative.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(player, player, new DecryptionDancePower(player), 1));
    }
}
