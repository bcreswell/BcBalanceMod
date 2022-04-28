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
import com.megacrit.cardcrawl.powers.FocusPower;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class DependencyInversion extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("DependencyInversion");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "      Dependency Inversion";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/dependencyInversion.png";
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
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Lose 1 Focus. NL Draw !M! cards. NL If your Focus is negative, draw 1 more.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new BcApplyPowerAction(new FocusPower(player, -1)));
        addToBot(new DrawCardAction(player, magicNumber));
        addToBot(new DependencyInversionDrawAction(player, 1));
    }
}
