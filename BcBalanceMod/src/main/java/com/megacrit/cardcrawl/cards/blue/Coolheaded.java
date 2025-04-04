//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Coolheaded extends BcSkillCardBase
{
    public static final String ID = "Coolheaded";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Coolheaded";
    }
    
    @Override
    public int getOrbCountToChannel()
    {
        return 1;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/coolheaded";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 1 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isFocusZero(),
            "Channel 1 Frost. NL Draw "+BcUtility.getCardCountString(magicNumber)+". NL #g0 #gFocus: Draw 1 more.");
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.getCurrentFocus() == 0;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChannelAction(new Frost()));
        
        int drawCount = magicNumber;
        if (BcUtility.getCurrentFocus() == 0)
        {
            drawCount++;
        }
        
        addToBot(new DrawCardAction(player, drawCount));
    }
}
