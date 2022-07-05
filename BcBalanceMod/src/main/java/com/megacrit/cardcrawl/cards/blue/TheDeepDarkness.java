//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DeepDarknessPower;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class TheDeepDarkness extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("TheDeepDarkness");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "The Deep Darkness";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/theDeepDarkness.png";
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
    public boolean getInnate()
    {
        return upgraded;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "End of turn: NL Trigger the passive on ALL Dark orbs. NL If you have an empty orb slot, Channel a Dark orb.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new DeepDarknessPower(player, 1)));
    }
}
