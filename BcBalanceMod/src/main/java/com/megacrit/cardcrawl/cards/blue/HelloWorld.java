//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.DiscoveryImprovedAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HelloWorld extends BcSkillCardBase
{
    public static final String ID = "Hello World";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/helloWorld.png";
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
    public String getDisplayName()
    {
        return "Hello World";
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Pick 1 of !M! upgraded common cards to create.";
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DiscoveryImprovedAction(magicNumber, false, true, null, CardRarity.COMMON, ID));
    }
}