package com.megacrit.cardcrawl.cards.curses;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Decay extends BcCurseCardBase
{
    public static final String ID = "Decay";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "curse/decay";
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
        return 6;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Unplayable. NL At the end of your turn, take !M! damage.";
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (dontTriggerOnUseCard)
        {
            addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }        
    }
    
    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
}
