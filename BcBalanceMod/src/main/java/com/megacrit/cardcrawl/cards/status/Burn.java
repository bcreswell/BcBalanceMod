package com.megacrit.cardcrawl.cards.status;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
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

public class Burn extends BcStatusCardBase
{
    public static final String ID = "Burn";
        
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "status/burn";
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 4;
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
            addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }
    
    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
}
