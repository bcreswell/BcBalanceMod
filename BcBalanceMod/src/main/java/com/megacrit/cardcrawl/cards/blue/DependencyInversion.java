//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

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
        return !upgraded ? 2 : 3;
    }
    
    public int getZeroFocusDrawCount()
    {
        return 2;
    }
    
    @Override
    public boolean getEthereal()
    {
        //ethereal means you can choose to get rid of it without losing focus.
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isFocusZero(),
            "Draw "+ getCardCountString(magicNumber)+". NL #g0 #gFocus: Draw "+ getZeroFocusDrawCount()+ " more. NL NL Lose 1 Focus.");
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        //precalculating the draw value rather than letting a queued action resolve it because that can
        // lead to this drawing itself even if done from a single draw action.
        int drawAmount = getMagicNumber();

        if (BcUtility.getCurrentFocus() == 0)
        {
            drawAmount += getZeroFocusDrawCount();
        }

        addToBot(new DrawCardAction(drawAmount));
        addToBot(new BcApplyPowerAction(new FocusPower(player, -1)));
    }
}
