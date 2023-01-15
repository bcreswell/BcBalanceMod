//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.*;
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
        return "Dependency Inversion";
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
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Lose 1 Focus. NL Draw a card. NL If your Focus is negative, draw 1 more.";
        }
        else
        {
            return "Lose 1 Focus. NL Draw !M! cards. NL If your Focus is negative, draw 1 more.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        //precalculating the draw value rather than letting a queued action resolve it because that can
        // lead to this drawing itself even if done from a single draw action.
        int drawAmount = magicNumber;
        if (BcUtility.getCurrentFocus() <= 0)
        {
            //will be negative by the time we draw
            drawAmount++;
        }
        
        addToBot(new BcApplyPowerAction(new FocusPower(player, -1)));
        addToBot(new DrawCardAction(drawAmount));
    }
}
