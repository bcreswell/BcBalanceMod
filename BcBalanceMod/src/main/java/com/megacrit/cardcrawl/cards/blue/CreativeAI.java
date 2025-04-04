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
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class CreativeAI extends BcPowerCardBase
{
    public static final String ID = "Creative AI";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/power/creative_ai";
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
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Start of turn: NL Create a random Skill. It's temporary and costs zero.";
        }
        else
        {
            return "Start of turn: NL Create a random Upgraded Skill. It's temporary and costs zero.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        BcCreativeAiPower2 power = new BcCreativeAiPower2(player, 1);
        if (upgraded)
        {
            power.upgrade();
        }
        addToBot(new BcApplyPowerAction(power));
    }
}
