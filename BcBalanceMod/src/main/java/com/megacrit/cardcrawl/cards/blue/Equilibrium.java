//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Equilibrium extends BcSkillCardBase
{
    public static final String ID = "Undo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/equilibrium";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 12 : 14;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        int b = getBlock();
        return "Retain your hand this turn. NL If you have zero Focus, gain " + (b + getMagicNumber()) + " block. Otherwise " + b + " Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new BcApplyPowerAction(new EquilibriumPower(player, 1)));
    }
    
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void applyPowers()
    {
        baseBlock = getBlock();
        if (BcUtility.getCurrentFocus() == 0)
        {
            baseBlock += getMagicNumber();
        }
        
        super.applyPowers();
        
        initializeDescription();
    }
}
