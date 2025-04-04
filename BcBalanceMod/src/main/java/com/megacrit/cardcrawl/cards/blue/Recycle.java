//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.RecycleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Recycle extends BcSkillCardBase
{
    public static final String ID = "Recycle";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/recycle";
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
    public String getBaseDescription()
    {
        if (block > 0)
        {
            return "Exhaust a card to Gain its cost as [B] NL and Gain !B! Block.";
        }
        else
        {
            return "Exhaust a card to NL Gain its cost as [B].";
        }
    }
    
//    @Override
//    public String getFootnote()
//    {
//        return BcUtility.ExhaustTargetFootnote;
//    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //does nothing if there's no target to exhaust
        if (player.hand.size() >= 2)
        {
            addToBot(new RecycleAction());
    
            if (block > 0)
            {
                addToBot(new GainBlockAction(player, block));
            }
        }
    }
}
