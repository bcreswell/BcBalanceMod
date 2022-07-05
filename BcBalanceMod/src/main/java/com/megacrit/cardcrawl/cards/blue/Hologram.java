//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hologram extends BcSkillCardBase
{
    public static final String ID = "Hologram";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Hologram";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/hologram";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 0 : 3;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Retrieve a card of your choice.";
        }
        else
        {
            return "Gain !B! Block. NL Retrieve a card of your choice.";
        }
    }
    
    @Override
    public boolean isARetrieveCard()
    {
        return true;
    }
    
    @Override
    public String getFootnote()
    {
        return "Can't target cards that have \"Retrieve\" in their description.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (upgraded)
        {
            addToBot(new GainBlockAction(player, player, block));
        }
        addToBot(new HologramAction());
    }
}
