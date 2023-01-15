//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.*;
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

public class Recursion extends BcSkillCardBase
{
    public static final String ID = "Redo";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/recursion";
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
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getNumberOfOrbsEvokedDirectly()
    {
        return 1;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Evoke your next Orb. NL Channel the Orb that was just Evoked.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RecursionAction(false,true));
    }
}
