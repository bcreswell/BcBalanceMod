//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Dualcast extends BcSkillCardBase
{
    public static final String ID = "Dualcast";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Dualcast";
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 1;
    }
    
    @Override
    public int getEvokeIterations()
    {
        return 2;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/dualcast";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.BASIC;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Evoke your next Orb twice.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new AnimateOrbAction(1));
        addToBot(new EvokeWithoutRemovingOrbAction(1, true));
        addToBot(new AnimateOrbAction(1));
        addToBot(new EvokeOrbAction(1));
    }
}
