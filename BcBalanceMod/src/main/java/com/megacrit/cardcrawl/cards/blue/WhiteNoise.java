//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.unique.CreateRandomCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhiteNoise extends BcSkillCardBase
{
    public static final String ID = "White Noise";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/white_noise";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public boolean getExhaust()
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
            return "Create a random temporary Power. NL Its costs zero.";
        }
        else
        {
            return "Create !M! random temporary Powers. NL They cost zero.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for(int i = 0; i < magicNumber; i++)
        {
            addToBot(
                new CreateRandomCardAction(
                    false,
                    false,
                    false,
                    true,
                    CardType.POWER,
                    null,
                    true,
                    true,
                    true,
                    null));
        }
    }
}