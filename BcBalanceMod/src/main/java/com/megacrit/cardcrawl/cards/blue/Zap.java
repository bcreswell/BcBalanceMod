//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Zap extends BcSkillCardBase
{
    public static final String ID = "Zap";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/zap";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.BASIC;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public int getChanneledOrbCount()
    {
        return getLightningCount();
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Channel !M! Lightning. NL If you have zero Focus, NL Channel 1 more.";
    }
    //endregion
    
    int getLightningCount()
    {
        if (BcUtility.getCurrentFocus() == 0)
        {
            return getMagicNumber() + 1;
        }
        else
        {
            return getMagicNumber();
        }
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return (BcUtility.getCurrentFocus() == 0);
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int lightningCount = getLightningCount();
        
        for (int i = 0; i < lightningCount; i++)
        {
            addToBot(new ChannelAction(new Lightning()));
        }
    }
}
