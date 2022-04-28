//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.*;  import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
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
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Chaos extends BcSkillCardBase
{
    public static final String ID = "Chaos";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/chaos";
    }
    
    protected void onInitialized()
    {
        showEvokeValue = true;
        showEvokeOrbCount = getMagicNumber();
    }
    
    protected void onUpgraded()
    {
        showEvokeOrbCount = magicNumber;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (magicNumber == 1)
        {
            return "Channel a random Orb. NL If you have zero Focus, channel 1 more.";
        }
        else
        {
            return "Channel !M! random Orbs. NL If you have zero Focus, channel 1 more.";
        }
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    //number of random orbs to Channel
    public int getMagicNumber()
    {
        if (!upgraded)
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }
        
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
        
        if (BcUtility.getCurrentFocus() == 0)
        {
            addToBot(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        }
    }
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
}
