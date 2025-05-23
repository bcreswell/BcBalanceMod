//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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

public class Concentrate extends BcSkillCardBase
{
    public static final String ID = "Concentrate";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/concentrate";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    public int getDiscardCount()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Discard " + getCardCountString(getDiscardCount()) + ". NL Gain " + getEnergyString(magicNumber) + ".";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DiscardAction(player, player, 2, false));
        addToBot(new GainEnergyAction(magicNumber));
    }
}
