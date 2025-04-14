package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Omniscience extends BcSkillCardBase
{
    public static final String ID = "Omniscience";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/omniscience";
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
    public int getCost()
    {
        return 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }

    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Pick a card from your Draw Pile. Play it !M! times then Exhaust it.";
        }
        else
        {
            return "Pick a card from your Hand, Draw Pile or Discard Pile. Play it !M! times then Exhaust it.";
        }
    }
    
    @Override
    public String getFootnote()
    {
        return "Can't pick Omniscience.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new OmniscienceAction(magicNumber, upgraded));
    }
}