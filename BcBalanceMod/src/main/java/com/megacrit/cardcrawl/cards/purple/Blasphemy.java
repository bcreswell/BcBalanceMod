package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

public class Blasphemy extends BcSkillCardBase
{
    public static final String ID = "Blasphemy";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/blasphemy";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 0 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 0)
        {
            return "Enter Divinity. NL Die next turn.";
        }
        else if (magicNumber == 1)
        {
            return "Enter Divinity. NL Draw a card. NL Die next turn.";
        }
        else
        {
            return "Enter Divinity. NL Draw !M! cards. NL Die next turn.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChangeStanceAction("Divinity"));
        
        if (magicNumber > 0)
        {
            addToBot(new DrawCardAction(magicNumber));
        }
        
        addToBot(new BcApplyPowerAction(new EndTurnDeathPower(player)));
    }
}
