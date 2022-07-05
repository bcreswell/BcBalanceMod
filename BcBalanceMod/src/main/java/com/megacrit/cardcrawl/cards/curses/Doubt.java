package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Doubt extends BcCurseCardBase
{
    public static final String ID = "Doubt";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "curse/doubt";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Unplayable. NL At the end of your turn, gain !M! Weak.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (dontTriggerOnUseCard)
        {
            addToTop(new BcApplyPowerAction(new WeakPower(AbstractDungeon.player, magicNumber, true)));
        }
    }
    
    public void triggerWhenDrawn()
    {
        addToBot(new SetDontTriggerAction(this, false));
    }
    
    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
}
