package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ChooseOneCard;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.RefactorAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class Refactor extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Refactor");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Refactor";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/refactor.png";
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        String upgradeDescription = "upgraded "; //upgraded ? "upgraded " : "";
        return "Exhaust a card and replace it with a random "+upgradeDescription+"card of the same color.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new RefactorAction(true));
    }
}
