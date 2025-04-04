package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

public class JustLucky extends BcAttackCardBase
{
    public static final String ID = "JustLucky";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/attack/just_lucky";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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

    //4*2*3 = 24
    //5*2*3 = 30
    //6*2*3 = 36
    @Override
    public int getDamage()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public int getMagicNumber()
    {
        //scry
        return !upgraded ? 2 : 3;
    }

    public int getCardDrawCount()
    {
        //return !upgraded ? 0 : 1;
        return 0;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        int cardDrawCount = getCardDrawCount();
        if (cardDrawCount > 0)
        {
            return "Draw "+cardDrawCount+". NL Scry " + BcUtility.getScryString(getMagicNumber()) + ". NL Gain !B! Block. NL Deal !D! damage. NL 50% chance: 3x damage.";
        }
        else
        {
            return "Scry " + BcUtility.getScryString(getMagicNumber()) + ". NL Gain !B! Block. NL Deal !D! damage. NL 50% chance: 3x damage.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int cardDrawCount = getCardDrawCount();
        if (cardDrawCount > 0)
        {
            addToBot(new DrawCardAction(cardDrawCount));
            //wait to give them time to see the drawn cards before scry window pops up
            addToBot(new TrueWaitAction(.8f));
        }

        addToBot(new ScryAction(magicNumber));
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new JustLuckyAction(monster, player, this));
    }
}
