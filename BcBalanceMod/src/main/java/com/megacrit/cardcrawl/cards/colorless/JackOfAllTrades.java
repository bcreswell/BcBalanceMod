//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.CreateRandomCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JackOfAllTrades extends BcSkillCardBase
{
    public static final String ID = "Jack Of All Trades";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/jack_of_all_trades";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Create !M! random upgraded uncommon colorless cards.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(
                new CreateRandomCardAction(
                    upgraded,
                    true,
                    false,
                    false,
                    null,
                    CardRarity.UNCOMMON,
                    false,
                    false,
                    false,
                    JackOfAllTrades.ID));
        }
    }
}
